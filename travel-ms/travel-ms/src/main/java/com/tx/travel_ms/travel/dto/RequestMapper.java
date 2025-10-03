package com.tx.travel_ms.travel.dto;

import com.tx.travel_ms.travel.HotelBooking;
import com.tx.travel_ms.travel.Request;
import com.tx.travel_ms.travel.TravelDetails;

public class RequestMapper {
    public static RequestDto toDto(Request request){
        if(request==null){
            return null;
        }
        RequestDto dto = new RequestDto();
        dto.setRequestId(request.getRequestId());
        dto.setEmployeeId(request.getEmployeeId());
        dto.setPurpose(request.getPurpose());
        dto.setRequestDate(request.getRequestDate());
        dto.setApprovalStatus(request.getApprovalStatus());
        dto.setApprovedBy(request.getApprovedBy());
        if(request.getTravelDetails()!=null){
            TravelDetailsDto travelDetailsDto = new TravelDetailsDto();
            travelDetailsDto.setFromCity(request.getTravelDetails().getFromCity());
            travelDetailsDto.setToCity(request.getTravelDetails().getToCity());
            travelDetailsDto.setDepartureDate(request.getTravelDetails().getDepartureDate());
            travelDetailsDto.setReturnDate(request.getTravelDetails().getReturnDate());
            dto.setTravelDetails(travelDetailsDto);
        }
        if(request.getHotelBooking()!=null){
            HotelBookingDto hotelBookingDto = new HotelBookingDto();
            hotelBookingDto.setCheckInDate(request.getHotelBooking().getCheckInDate());
            hotelBookingDto.setCheckOutDate(request.getHotelBooking().getCheckOutDate());
            hotelBookingDto.setPreferredAddress(request.getHotelBooking().getPreferredAddress());
            dto.setHotelBooking(hotelBookingDto);
        }
        return dto;
    }
    public static Request toEntity(RequestDto dto){
        if(dto ==null){
            return null;
        }
        Request request = new Request();
        request.setEmployeeId(dto.getEmployeeId());
        request.setPurpose(dto.getPurpose());
        request.setRequestDate(dto.getRequestDate());
        request.setApprovalStatus(dto.getApprovalStatus());
        request.setApprovedBy(dto.getApprovedBy());
        if(dto.getTravelDetails()!=null){
            TravelDetails travelDetails = new TravelDetails();
            travelDetails.setFromCity(dto.getTravelDetails().getFromCity());
            travelDetails.setToCity(dto.getTravelDetails().getToCity());
            travelDetails.setDepartureDate(dto.getTravelDetails().getDepartureDate());
            travelDetails.setReturnDate(dto.getTravelDetails().getReturnDate());
            request.setTravelDetails(travelDetails);
            travelDetails.setRequest(request);
        }
        if(dto.getHotelBooking()!=null){
            HotelBooking hotelBooking = new HotelBooking();
            hotelBooking.setCheckInDate(dto.getHotelBooking().getCheckInDate());
            hotelBooking.setCheckOutDate(dto.getHotelBooking().getCheckOutDate());
            hotelBooking.setPreferredAddress(dto.getHotelBooking().getPreferredAddress());
            request.setHotelBooking(hotelBooking);
            hotelBooking.setRequest(request);
        }
        return request;
    }
}
