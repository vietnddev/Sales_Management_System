package com.flowiee.app.dto;

import com.flowiee.app.entity.TicketExport;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TicketExportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String exporter;
    private Date exportTime;
    private String note;
    private String status;
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