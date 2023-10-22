package br.dev.kuhn.UserStorageProvider;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderFactory;

public class RemoteUserStorageProviderFactory implements ProviderFactory<RemoteUserStorageProvider> {

    public static final String PROVIDER_NAME = "my-remote-user-storage-provider";

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public RemoteUserStorageProvider create(KeycloakSession session) {
        return new RemoteUserStorageProvider(session,
                buildHttpClient("http://localhost:8099"));
    }

    @Override
    public void init(Scope config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    private UsersApiService buildHttpClient(String uri) {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(uri);

        return target.proxyBuilder(UsersApiService.class)
                .classloader(UsersApiService.class.getClassLoader()).build();

    }

}
