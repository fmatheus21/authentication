package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.HttpStatusConstant;
import com.fmatheus.app.controller.constant.OperationConstant;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.exception.handler.MessageResponseHandler;
import com.fmatheus.app.controller.exception.message.MessageResponse;
import com.fmatheus.app.controller.exception.swagger.BadRequest;
import com.fmatheus.app.controller.exception.swagger.Forbidden;
import com.fmatheus.app.controller.exception.swagger.ServerError;
import com.fmatheus.app.controller.exception.swagger.Unauthorized;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


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
        var result = rule.findAll(pageable, filter);
        return !result.isEmpty() ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = OperationConstant.GET, description = OperationConstant.DESCRIPTION_GET,
            tags = {OperationConstant.USER_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.OK_NUMBER, description = HttpStatusConstant.OK,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseHandler.class))),
            @ApiResponse(responseCode = HttpStatusConstant.BAD_REQUEST_NUMBER, description = HttpStatusConstant.BAD_REQUEST,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    @GetMapping(ResourceConstant.ID)
    public ResponseEntity<UserDtoResponse> findById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(rule.findById(id));
    }


    @Operation(summary = OperationConstant.POST, description = OperationConstant.DESCRIPTION_POST,
            tags = {OperationConstant.USER_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.CREATED_NUMBER, description = HttpStatusConstant.CREATED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = HttpStatusConstant.BAD_REQUEST_NUMBER, description = HttpStatusConstant.BAD_REQUEST,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    @PostMapping
    public ResponseEntity<MessageResponseHandler> create(@RequestBody @Valid UserDtoRequest request, HttpServletResponse response) {
        return rule.create(request, response);
    }


}