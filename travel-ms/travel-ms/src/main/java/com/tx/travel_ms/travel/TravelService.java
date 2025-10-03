package com.tx.travel_ms.travel;

import com.tx.travel_ms.travel.dto.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TravelService {

    @Autowired
    private final RequestRepository requestRepository;
    @Autowired
    private final EmployeeServiceClient employeeServiceClient;

    public TravelService(RequestRepository requestRepository,EmployeeServiceClient employeeServiceClient){
        this.requestRepository=requestRepository;
        this.employeeServiceClient=employeeServiceClient;
    }
    public Request createRequest(Request request){
        try{
            employeeServiceClient.getEmployeeById(request.getEmployeeId());
        }catch (Exception e){
            throw new RuntimeException("Employee not found by ID: "+request.getEmployeeId());
        }
        request.setApprovalStatus(RequestStatus.PENDING);
        if(request.getTravelDetails()!=null){
            LocalDate departureDate=request.getTravelDetails().getDepartureDate();
            LocalDate returnDate=request.getTravelDetails().getReturnDate();
            if(departureDate!=null&&returnDate!=null&&returnDate.isBefore(departureDate)){
                throw new IllegalArgumentException("Return Date can not be before the Departure Date");
            }
            request.getTravelDetails().setRequest(request);
        }
        if(request.getHotelBooking()!=null){
            LocalDate checkIn=request.getHotelBooking().getCheckInDate();
            LocalDate checkOut=request.getHotelBooking().getCheckOutDate();
            if(checkIn!=null&&checkOut!=null&&!checkOut.isAfter(checkIn)){
                throw new IllegalArgumentException("Check-In Date can not be After the Check-Out Date");
            }
            request.getHotelBooking().setRequest(request);
        }
        return requestRepository.save(request);
    }
    public Optional<Request> getRequestById(Long id){
        return  requestRepository.findById(id);
    }
    public List<Request> getRequestByEmployee(Long employeeId){
        return requestRepository.findByEmployeeId(employeeId);
    }
    public void deleteRequestsByEmployeeId(Long employeeId){
        requestRepository.deleteAllByEmployeeId(employeeId);
    }
}
