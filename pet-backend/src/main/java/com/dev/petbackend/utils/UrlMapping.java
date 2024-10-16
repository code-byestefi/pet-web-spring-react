package com.dev.petbackend.utils;

public class UrlMapping {
    public static final String API = "/api/v1";
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER_BY_ID = "/delete/{userId}";
    public static final String GET_ALL_USERS = "/all-users";
    /*========================== Start Appointment API =================================*/
    public static final String APPOINTMENTS = API+"/appointments";
    public static final String ALL_APPOINTMENT = "/all" ;
    public static final String BOOK_APPOINTMENT = "/book-appointment" ;
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/{id}" ;
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/{appointmentNo}/appointment" ;
    public static final String DELETE_APPOINTMENT = "/appointment/{id}/delete" ;
    public static final String UPDATE_APPOINTMENT = "/appointment/{id}/update";
    /*========================= End Appointment API ================================*/
    /*========================== Start Pet API =================================*/
    public static final String PETS = API+"/pets";
    public static final String SAVE_PETS_FOR_APPOINTMENT = "/save-pets/";
    public static final String DELETE_PET_BY_ID = "/pet/{petId}/delete";
    public static final String GET_PET_BY_ID = "/pet/{petId}/pet";
    public static final String UPDATE_PET = "/pet/{petId}/update";

    /*========================== Start Photo API =================================*/
    public static final String PHOTOS = API+"/photos";
    public static final String UPLOAD_PHOTO = "/photo/upload";
    public static final String UPDATE_PHOTO = "/photo/{photoId}/update";
    public static final String DELETE_PHOTO = "/photo/{photoId}/user/{userId}/delete";
    public static final String GET_PHOTO_BY_ID = "/photo/{photoId}/photo";
    /*========================== Start Review API =================================*/
    public static final String REVIEWS = API+"/reviews";
    public static final String SUBMIT_REVIEW = "/submit-review";
    public static final String GET_USER_REVIEWS = "/user/{userId}/reviews";
    public static final String UPDATE_REVIEW = "/review/{reviewId}/update";
    public static final String DELETE_REVIEW = "review/{reviewId}/delete";
    public static final String GET_AVERAGE_RATING = "/vet/{vetId}/get-average-rating";

    /*============================ Start Veterinarian API ===================================*/

    public static final String VETERINARIANS = API+"/veterinarians";
    public static final String GET_ALL_VETERINARIANS = "/get-all-veterinarians";
    public static final String SEARCH_VETERINARIAN_FOR_APPOINTMENT = "/search-veterinarian";
    ;
    /*============================ End Veterinarian API ===================================*/
}
