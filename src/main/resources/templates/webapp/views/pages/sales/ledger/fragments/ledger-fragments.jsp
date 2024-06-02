<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <body>
        <div th:fragment="modalAddReceipt">
            <div class="modal fade" id="modalAddReceipt">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content text-left">
                        <form th:action="@{/ledger/receipt/insert}" th:object="${ledgerReceipt}" method="post">
                            <div class="modal-header">
                                <strong class="modal-title">Thêm mới phiếu thu</strong>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-6 form-group">
                                        <label>Nhóm người nộp</label>
                                        <select class="custom-select" name="receiverGroup" required>
                                            <option th:each="list : ${listReceiptGroups}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Tên người nộp</label>
                                        <input type="text" class="form-control" name="receiverName" required/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Loại phiếu thu</label>
                                        <select class="custom-select" name="receiptType" required>
                                            <option th:each="list : ${listReceiptTypes}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Mã phiếu</label>
                                        <input type="text" class="form-control" name="receiptCode"/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Giá trị</label>
                                        <input type="text" class="form-control" required name="receiptAmount"/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Hình thức thanh toán</label>
                                        <select class="custom-select" name="paymentMethod" required>
                                            <option th:each="list : ${listPaymentMethods}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label>Ghi chú</label>
                                        <textarea type="text" class="form-control" name="description" required></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer justify-content-end">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div th:fragment="modalAddPayment">
            <div class="modal fade" id="modalAddPayment">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content text-left">
                        <form th:action="@{/ledger/payment/insert}" th:object="${ledgerPayment}" method="post">
                            <div class="modal-header">
                                <strong class="modal-title">Thêm mới phiếu chi</strong>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-6 form-group">
                                        <label>Nhóm người nhận</label>
                                        <select class="custom-select" name="payerGroup" required>
                                            <option th:each="list : ${listPaymentGroups}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Tên người nhận</label>
                                        <input type="text" class="form-control" name="payerName" required/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Loại phiếu chi</label>
                                        <select class="custom-select" name="paymentType" required>
                                            <option th:each="list : ${listPaymentTypes}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Mã phiếu</label>
                                        <input type="text" class="form-control" name="paymentCode"/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Giá trị</label>
                                        <input type="text" class="form-control" required name="paymentAmount"/>
                                    </div>
                                    <div class="col-6 form-group">
                                        <label>Hình thức thanh toán</label>
                                        <select class="custom-select" name="paymentMethod" required>
                                            <option th:each="list : ${listPaymentMethods}" th:value="${list.id}" th:text="${list.name}"></option>
                                        </select>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label>Ghi chú</label>
                                        <textarea type="text" class="form-control" name="description" required></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer justify-content-end">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>