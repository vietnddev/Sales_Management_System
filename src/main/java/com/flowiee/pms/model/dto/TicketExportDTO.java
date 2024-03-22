package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.TicketExport;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketExportDTO extends TicketExport implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<OrderDTO> listOrderDTO;

    public static TicketExportDTO fromTicketExport(TicketExport t) {
        TicketExportDTO dto = new TicketExportDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setExporter(t.getExporter());
        dto.setExportTime(t.getExportTime());
        dto.setNote(t.getNote());
        dto.setStatus(t.getStatus());
        dto.setListOrderDTO(OrderDTO.fromOrders(t.getListOrders()));
        return dto;
    }

    public static List<TicketExportDTO> fromTickerExports(List<TicketExport> ts) {
        List<TicketExportDTO> list = new ArrayList<>();
        for (TicketExport t : ts) {
            list.add(TicketExportDTO.fromTicketExport(t));
        }
        return list;
    }
}