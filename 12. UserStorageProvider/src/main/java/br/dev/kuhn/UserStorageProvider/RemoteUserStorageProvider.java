package br.dev.kuhn.UserStorageProvider;

import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientScopeModel;
import org.keycloak.models.FederatedIdentityModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserConsentModel;
import org.keycloak.models.UserManager;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.user.UserLookupProvider;

public class RemoteUserStorageProvider implements UserProvider, CredentialInputValidator {

    private KeycloakSession session;
    private UsersApiService usersService;

    public RemoteUserStorageProvider(KeycloakSession session, UsersApiService usersService) {
        this.session = session;
        this.usersService = usersService;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {

        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if (!supportsCredentialType(credentialType)) {
            return false;
        }

        return ! user.credentialManager().getStoredCredentialsByTypeStream(credentialType).toList().isEmpty();
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        VerifyPasswordResponse verifyPasswordResponse = usersService.verifyUserPassword(user.getUsername(),
                credentialInput.getChallengeResponse());

        if (verifyPasswordResponse == null)
            return false;

        return verifyPasswordResponse.getResult();
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();

        return getUserByUsername(realm, username);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {

        UserModel returnValue = null;

        User user = usersService.getUserDetails(username);

        if (user != null) {
            returnValue = createUserModel(username, realm);
        }

        return returnValue;
    }

    private UserModel createUserModel(String username, RealmModel realm) {
        
	}

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult,
            Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult,
            Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult,
            Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel addUser(RealmModel realm, String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void grantToAllUsers(RealmModel realm, RoleModel role) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNotBeforeForUser(RealmModel realm, UserModel user, int notBefore) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getNotBeforeOfUser(RealmModel realm, UserModel user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UserModel getServiceAccount(ClientModel client) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel addUser(RealmModel realm, String id, String username, boolean addDefaultRoles,
            boolean addDefaultRequiredActions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeImportedUsers(RealmModel realm, String storageProviderId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unlinkUsers(RealmModel realm, String storageProviderId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addConsent(RealmModel realm, String userId, UserConsentModel consent) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserConsentModel getConsentByClient(RealmModel realm, String userId, String clientInternalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stream<UserConsentModel> getConsentsStream(RealmModel realm, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateConsent(RealmModel realm, String userId, UserConsentModel consent) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean revokeConsentForClient(RealmModel realm, String userId, String clientInternalId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addFederatedIdentity(RealmModel realm, UserModel user, FederatedIdentityModel socialLink) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean removeFederatedIdentity(RealmModel realm, UserModel user, String socialProvider) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateFederatedIdentity(RealmModel realm, UserModel federatedUser,
            FederatedIdentityModel federatedIdentityModel) {
        // TODO Auto-generated method stub

    }

    @Override
    public Stream<FederatedIdentityModel> getFederatedIdentitiesStream(RealmModel realm, UserModel user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FederatedIdentityModel getFederatedIdentity(RealmModel realm, UserModel user, String socialProvider) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserModel getUserByFederatedIdentity(RealmModel realm, FederatedIdentityModel socialLink) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void preRemove(RealmModel realm) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(RealmModel realm, IdentityProviderModel provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(RealmModel realm, RoleModel role) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(RealmModel realm, GroupModel group) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(RealmModel realm, ClientModel client) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(ProtocolMapperModel protocolMapper) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(ClientScopeModel clientScope) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preRemove(RealmModel realm, ComponentModel component) {
        // TODO Auto-generated method stub

    }

}
