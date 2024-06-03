package com.flowiee.pms.validate.authorize.system;

public interface IVldModuleSystem {
    boolean readPermission(boolean throwException);

    boolean readAccount(boolean throwException);

    boolean insertAccount(boolean throwException);

    boolean updateAccount(boolean throwException);

    boolean deleteAccount(boolean throwException);

    boolean readLog(boolean throwException);

    boolean readConfig(boolean throwException);

    boolean updateConfig(boolean throwException);

    boolean readGroupAccount(boolean throwException);

    boolean insertGroupAccount(boolean throwException);

    boolean updateGroupAccount(boolean throwException);

    boolean deleteGroupAccount(boolean throwException);
}