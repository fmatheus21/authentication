package com.fmatheus.app.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fmatheus.app.controller.enumerable.AppPropertiesEnum;
import com.fmatheus.app.controller.enumerable.FormatEnum;
import com.fmatheus.app.controller.storage.FileServiceStorage;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.controller.util.FileUtil;
import com.fmatheus.app.controller.util.LocalDatetUtil;
import com.fmatheus.app.model.entity.UserEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.File;
import java.util.Objects;

/**
 * @author Fernando Matheus
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "username", "createdAt", "updatedAt", "status", "person", "contact", "address", "userStatus", "permission", "idRole", "role", "avatar"})
public class UserDtoResponse extends RepresentationModel<UserDtoResponse> {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("person")
    private PersonDtoResponse person;

    @JsonProperty("contact")
    private ContactDtoResponse contact;

    @JsonProperty("address")
    private AddressDtoResponse address;

    @JsonProperty("userStatus")
    private UserStatusDtoResponse userStatus;

    @JsonProperty("role")
    private String role;

    @JsonProperty("idRole")
    private int idRole;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("permission")
    private PermissionDtoResponse permission;


    public static UserDtoResponse converterDto(UserEntity entity) {
        return Objects.nonNull(entity) ? UserDtoResponse.builder()
                .id(entity.getId())
                .username(AppUtil.convertAllLowercaseCharacters(entity.getUsername()))
                .createdAt(LocalDatetUtil.converterLocalDateTimeToString(entity.getCreatedAt(), FormatEnum.DATE_TIME))
                .updatedAt(LocalDatetUtil.converterLocalDateTimeToString(entity.getUpdatedAt(), FormatEnum.DATE_TIME))
                .status(entity.getUserStatusEntity().getId() == 1)
                .person(PersonDtoResponse.converterDto(entity.getIdPerson()))
                .contact(ContactDtoResponse.converterDto(entity.getIdPerson().getContactEntity()))
                .address(AddressDtoResponse.converterDto(entity.getIdPerson().getAddressEntity()))
                .userStatus(UserStatusDtoResponse.converterDto(entity.getUserStatusEntity()))
                .role(AppUtil.convertFirstUppercaseCharacter(entity.getPermissions().stream().findFirst().get().getName()))
                .idRole(entity.getPermissions().stream().findFirst().get().getId())
                .permission(PermissionDtoResponse.converterDto(entity.getPermissions().stream().findFirst().get()))
                .build() : null;
    }

    /**
     * Converte uma entidade em dto.
     *
     * @param entity - Entidade
     * @return UserDtoResponse
     * @author Fernando Matheus
     */
    public static UserDtoResponse converterDtoImage(UserEntity entity, FileServiceStorage fileServiceStorage) {

        var avatar = Objects.nonNull(fileServiceStorage) ?
                fileServiceStorage.returnPath(AppPropertiesEnum.PATH_AVATAR).concat(File.separator).concat(entity.getAvatar())
                : null;

        return Objects.nonNull(entity) ? UserDtoResponse.builder()
                .id(entity.getId())
                .username(AppUtil.convertAllLowercaseCharacters(entity.getUsername()))
                .createdAt(LocalDatetUtil.converterLocalDateTimeToString(entity.getCreatedAt(), FormatEnum.DATE_TIME))
                .updatedAt(LocalDatetUtil.converterLocalDateTimeToString(entity.getUpdatedAt(), FormatEnum.DATE_TIME))
                .status(entity.getUserStatusEntity().getId() == 1)
                .person(PersonDtoResponse.converterDto(entity.getIdPerson()))
                .contact(ContactDtoResponse.converterDto(entity.getIdPerson().getContactEntity()))
                .address(AddressDtoResponse.converterDto(entity.getIdPerson().getAddressEntity()))
                .userStatus(UserStatusDtoResponse.converterDto(entity.getUserStatusEntity()))
                .avatar(FileUtil.converterImageToBase64(avatar))
                .role(AppUtil.convertFirstUppercaseCharacter(entity.getPermissions().stream().findFirst().get().getName()))
                .idRole(entity.getPermissions().stream().findFirst().get().getId())
                .permission(PermissionDtoResponse.converterDto(entity.getPermissions().stream().findFirst().get()))
                .build() : null;
    }


}
