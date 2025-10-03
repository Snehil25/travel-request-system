package com.tx.travel_ms.travel;

import com.tx.travel_ms.travel.dto.RequestDto;
import com.tx.travel_ms.travel.dto.RequestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class TravelController {
    private final TravelService travelService;
    public TravelController(TravelService travelService){
        this.travelService=travelService;
    }
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody RequestDto requestDto){
        try {
            Request requestToSave= RequestMapper.toEntity(requestDto);
            Request createdRequest=travelService.createRequest(requestToSave);
            return ResponseEntity.ok(RequestMapper.toDto(createdRequest));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id){
        return travelService.getRequestById(id)
                .map(RequestMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<List<RequestDto>> getRequestByEmployee(@PathVariable Long employeeId){
        List<Request> requests=travelService.getRequestByEmployee(employeeId);
        List<RequestDto> requestDto=requests.stream()
                .map(RequestMapper::toDto)
                .toList();
        return ResponseEntity.ok(requestDto);
    }
    @DeleteMapping("/by-employee/{employeeId}")
    public ResponseEntity<Void> deleteRequestByEmployee(@PathVariable Long employeeId){
        travelService.deleteRequestsByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
