package com.fmatheus.app.controller.constant;

public class OperationConstant {

    private OperationConstant() {
        throw new IllegalStateException("OperationConstant class");
    }

    public static final String LIST = "Listar Registros";
    public static final String GET = "Consultar Registro";
    public static final String POST = "Criar Registro";
    public static final String PUT = "Atualizar Registro";
    public static final String DELETE = "Excluir Registro";
    public static final String DESCRIPTION_LIST = "Retorna uma lista de registros";
    public static final String DESCRIPTION_GET = "Retorna um registro";
    public static final String DESCRIPTION_POST = "Criar um registro";
    public static final String DESCRIPTION_PUT = "Atualizar um registro";
    public static final String DESCRIPTION_DELETE = "Excluir um registro";

    public static final String USER_TAG = "Usuário";
    public static final String USER_LIST_DESCRIPTION = "Retorna uma lista de usuários com paginação.";
    public static final String USER_GET_DESCRIPTION = "Retorna um registro de usuário.";
    public static final String USER_POST_DESCRIPTION = "Cria um novo registro de usuário.";
    public static final String USER_PUT_DESCRIPTION = "Alterar registro de usuário.";
    public static final String USER_PUT_STATUS_DESCRIPTION = "Altera o status do usuário.";
    public static final String USER_PUT_RECOVER_DESCRIPTION = "Recupera senha.";

}