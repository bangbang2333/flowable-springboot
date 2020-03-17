package com.creativec.common.base;

public interface BaseConstant {

    String ENTITY_PK_NAME = "kid";

    String CGI_URI_PREFIX = "/cgi";
    String RESTFUL_PREFIX = "/rest";
    String RPC_PREFIX = "/rpc";
    String CLINET_TYPE = "demo";
    String API_SUCCESS_CODE = "OK";
    String API_SUCCESS_NAME = "Success";
    String API_SUCCESS_MESSAGE = "";

    String KEY_TYPE_EXTERNAL = "1";
    String KEY_TYPE_INTERNAL = "2";

    String AUTH_CACHE_NAME_SYSTEM_CONFIG = "Auth.System";
    String AUTH_CACHE_NAME_ROLE_PERMISSION = "Auth.RolePermission";

    public enum PASSWORD_ALGORITHM {
        md5, bcrypt, none
    }

    String RELATION_TYPE_BELONG = "1";

    String TREE_PATH_SEPARATOR = "//";

    enum AUTH_DATA_KEY {
        userId, userAccountId, clientCompanyId, companyId, authClientId, sessionId, systemId, organizationId, domainId, authorities, securityMode
    }

    long DEFAULT_EXPORT_LIMIT_SIZE = 1000;

    String HEADER_SKIP_NESTED_RESPONSE = "SkipNestedResponse";

    String AuthorizationServerCacheManager = "authorizationServerRedisCacheManager";


    String PreAuthorizePermitAll = "permitAll()";

    //token有效期默认2小时
    int tokenExpire = 3600 * 2;
}
