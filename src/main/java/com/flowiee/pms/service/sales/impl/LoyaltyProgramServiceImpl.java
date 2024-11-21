package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.sales.LoyaltyProgram;
import com.flowiee.pms.entity.sales.LoyaltyTransaction;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.repository.sales.LoyaltyProgramRepository;
import com.flowiee.pms.repository.sales.LoyaltyTransactionRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.service.sales.LoyaltyProgramService;
import com.flowiee.pms.utils.constants.LoyaltyTransactionType;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoyaltyProgramServiceImpl extends BaseService implements LoyaltyProgramService {
    private final LoyaltyTransactionRepository loyaltyTransactionRepository;
    private final LoyaltyProgramRepository loyaltyProgramRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<LoyaltyProgram> findAll() {
        return loyaltyProgramRepository.findAll();
    }

    @Override
    public Optional<LoyaltyProgram> findById(Long programId) {
        return loyaltyProgramRepository.findById(programId);
    }

    @Override
    public LoyaltyProgram findById(Long programId, boolean throwException) {
        Optional<LoyaltyProgram> loyaltyProgramOpt = this.findById(programId);
        if (loyaltyProgramOpt.isPresent()) {
            return loyaltyProgramOpt.get();
        }
        if (throwException) {
            throw new ResourceNotFoundException("Loyalty program does not exist!");
        } else
            return null;
    }

    @Override
    public LoyaltyProgram save(LoyaltyProgram program) {
        LocalDateTime lvStartDate = program.getStartDate();
        LocalDateTime lvEndDate = program.getEndDate();
        if (!lvStartDate.isBefore(lvEndDate))
            throw new BadRequestException("The end time must be greater than the start time!");

        return loyaltyProgramRepository.save(program);
    }

    @Override
    public LoyaltyProgram update(LoyaltyProgram updateProgram, Long loyaltyProgramId) {
        Optional<LoyaltyProgram> existingProgramOpt = findById(loyaltyProgramId);
        LoyaltyProgram existingProgram = existingProgramOpt.get();

        if (!existingProgram.isExpired()) {
            existingProgram.setName(updateProgram.getName());
            existingProgram.setDescription(updateProgram.getDescription());
            existingProgram.setPointConversionRate(updateProgram.getPointConversionRate());
            existingProgram.setStartDate(updateProgram.getStartDate());
            existingProgram.setEndDate(updateProgram.getEndDate());
            existingProgram.setActive(updateProgram.isActive());
        }

        return loyaltyProgramRepository.save(existingProgram);
    }

    @Override
    public String delete(Long loyaltyProgramId) {
        Optional<LoyaltyProgram> existingProgramOpt = findById(loyaltyProgramId);
        LoyaltyProgram existingProgram = existingProgramOpt.get();
        if (!existingProgram.getLoyaltyTransactionList().isEmpty()) {
            throw new BadRequestException("The program has a transaction that does not allow deletion!");
        }
        loyaltyProgramRepository.deleteById(loyaltyProgramId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<LoyaltyProgram> getActivePrograms() {
        return loyaltyProgramRepository.findActiveProgram();
    }

    @Override
    public LoyaltyProgram enableProgram(Long loyaltyProgramId) {
        LoyaltyProgram existingProgram = findById(loyaltyProgramId, true);
        existingProgram.setActive(true);
        return loyaltyProgramRepository.save(existingProgram);
    }

    @Override
    public LoyaltyProgram disableProgram(Long loyaltyProgramId) {
        LoyaltyProgram existingProgram = findById(loyaltyProgramId, true);
        existingProgram.setActive(false);
        return loyaltyProgramRepository.save(existingProgram);
    }

    @Transactional
    @Override
    public void accumulatePoints(Long pCustomerId, Long pProgramId, int points) {
        LoyaltyProgram lvLoyaltyProgram = this.findById(pProgramId, true);
        if (!lvLoyaltyProgram.isActive() && lvLoyaltyProgram.isExpired())
            throw new BadRequestException("Loyalty program is inactive or expired now!");

        Optional<Customer> lvCustomerOpt = customerRepository.findById(pCustomerId);
        if (lvCustomerOpt.isEmpty())
            throw new ResourceNotFoundException("Customer not found!");
        if (points <= 0)
            throw new BadRequestException("Points must be greater than zero!");

        Customer lvCustomer = lvCustomerOpt.get();

        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setCustomer(lvCustomer);
        transaction.setLoyaltyProgram(lvLoyaltyProgram);
        transaction.setTransactionType(LoyaltyTransactionType.ACCUMULATE);
        transaction.setPoints(points);
        transaction.setTransactionDate(LocalDateTime.now());
        loyaltyTransactionRepository.save(transaction);

        lvCustomer.setBonusPoints(lvCustomer.getBonusPoints() + points);
        customerRepository.save(lvCustomer);
    }

    @Transactional
    @Override
    public void redeemPoints(Long pCustomerId, int pointsToRedeem) {
        Optional<Customer> lvCustomerOpt = customerRepository.findById(pCustomerId);
        if (lvCustomerOpt.isEmpty())
            throw new ResourceNotFoundException("Customer not found!");

        Customer lvCustomer = lvCustomerOpt.get();
        if (lvCustomer.getBonusPoints() < pointsToRedeem)
            throw new RuntimeException("Insufficient points");

        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setCustomer(lvCustomer);
        transaction.setTransactionType(LoyaltyTransactionType.REDEEM);
        transaction.setPoints(pointsToRedeem);
        transaction.setTransactionDate(LocalDateTime.now());
        loyaltyTransactionRepository.save(transaction);

        // Deduct points
        lvCustomer.setBonusPoints(lvCustomer.getBonusPoints() - pointsToRedeem);
        customerRepository.save(lvCustomer);
    }
}