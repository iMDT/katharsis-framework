package io.katharsis.dispatcher.controller.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.katharsis.dispatcher.controller.HttpMethod;
import io.katharsis.queryspec.internal.QueryAdapter;
import io.katharsis.repository.RepositoryMethodParameterProvider;
import io.katharsis.request.dto.DataBody;
import io.katharsis.request.dto.RequestBody;
import io.katharsis.request.path.JsonPath;
import io.katharsis.request.path.ResourcePath;
import io.katharsis.resource.exception.RequestBodyException;
import io.katharsis.resource.exception.RequestBodyNotFoundException;
import io.katharsis.resource.exception.ResourceNotFoundException;
import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.resource.registry.repository.adapter.ResourceRepositoryAdapter;
import io.katharsis.response.HttpStatus;
import io.katharsis.response.JsonApiResponse;
import io.katharsis.response.ResourceResponseContext;
import io.katharsis.utils.parser.TypeParser;

public class ResourcePost extends ResourceUpsert {

    public ResourcePost(ResourceRegistry resourceRegistry, TypeParser typeParser, ObjectMapper objectMapper) {
        super(resourceRegistry, typeParser, objectMapper);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Check if it is a POST request for a resource.
     */
    @Override
    public boolean isAcceptable(JsonPath jsonPath, String requestType) {
        return jsonPath.isCollection() &&
            jsonPath instanceof ResourcePath &&
            HttpMethod.POST.name()
                .equals(requestType);
    }

    @Override
    public ResourceResponseContext handle(JsonPath jsonPath, QueryAdapter queryAdapter,
                                          RepositoryMethodParameterProvider parameterProvider, RequestBody requestBody){
        String resourceEndpointName = jsonPath.getResourceName();
        RegistryEntry endpointRegistryEntry = resourceRegistry.getEntry(resourceEndpointName);
        if (endpointRegistryEntry == null) {
            throw new ResourceNotFoundException(resourceEndpointName);
        }
        if (requestBody == null) {
            throw new RequestBodyNotFoundException(HttpMethod.POST, resourceEndpointName);
        }
        if (requestBody.isMultiple()) {
            throw new RequestBodyException(HttpMethod.POST, resourceEndpointName, "Multiple data in body");
        }

        DataBody dataBody = requestBody.getSingleData();
        if (dataBody == null) {
            throw new RequestBodyException(HttpMethod.POST, resourceEndpointName, "No data field in the body.");
        }
        RegistryEntry bodyRegistryEntry = resourceRegistry.getEntry(dataBody.getType());
        verifyTypes(HttpMethod.POST, resourceEndpointName, endpointRegistryEntry, bodyRegistryEntry);
        Object newResource = newResource(bodyRegistryEntry.getResourceInformation(), dataBody);

        setId(dataBody, newResource, bodyRegistryEntry.getResourceInformation());
        setAttributes(dataBody, newResource, bodyRegistryEntry.getResourceInformation());
        ResourceRepositoryAdapter resourceRepository = endpointRegistryEntry.getResourceRepository(parameterProvider);
        setRelations(newResource, bodyRegistryEntry, dataBody, queryAdapter, parameterProvider);
        JsonApiResponse response = resourceRepository.create(newResource, queryAdapter);

        return new ResourceResponseContext(response, jsonPath, queryAdapter, HttpStatus.CREATED_201);
    }
}
