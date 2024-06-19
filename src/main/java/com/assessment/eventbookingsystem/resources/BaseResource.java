package com.assessment.eventbookingsystem.resources;

public interface BaseResource {
    String PATH_ID_PATHVARIABLE = "/{id}";

    String ID_PATHVARIABLE = "{id}";

    String RELATIVEPATH = "/";

    String DELETEDBYID = PATH_ID_PATHVARIABLE;
    String FINDBYID = "find/{id}";

}
