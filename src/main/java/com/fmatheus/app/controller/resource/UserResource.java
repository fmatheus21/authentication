package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.HttpStatusConstant;
import com.fmatheus.app.controller.constant.OperationConstant;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.dto.response.swagger.Forbidden;
import com.fmatheus.app.controller.dto.response.swagger.ServerError;
import com.fmatheus.app.controller.dto.response.swagger.Unauthorized;
import com.fmatheus.app.controller.rule.UserRule;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ResourceConstant.USERS)
public class UserResource {

    @Autowired
    private UserRule rule;


    @Operation(summary = OperationConstant.LIST, description = OperationConstant.USER_LIST_DESCRIPTION,
            tags = {OperationConstant.USER_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.OK_NUMBER, description = HttpStatusConstant.OK,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoResponse.class))),
            @ApiResponse(responseCode = HttpStatusConstant.NO_CONTENT_NUMBER, description = HttpStatusConstant.NO_CONTENT,
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    @GetMapping
    public ResponseEntity<Page<UserDtoResponse>> list(Pageable pageable, RepositoryFilter filter) {
        return rule.findAll(pageable, filter);
    }


}