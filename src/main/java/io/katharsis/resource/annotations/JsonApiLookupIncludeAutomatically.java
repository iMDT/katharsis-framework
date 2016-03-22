package io.katharsis.resource.annotations;

import io.katharsis.queryParams.QueryParams;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * This annotation is used to make automatic value assignment using a defined relationship repository if such repository
 * is available. It can be used to leave resource relationships from a resource repository not populated and make
 * Katharsis call either {@link io.katharsis.repository.RelationshipRepository#findOneTarget(Serializable, String, QueryParams)}
 * or {@link io.katharsis.repository.RelationshipRepository#findManyTargets(Serializable, String, QueryParams)}
 * depending on the multiplicity of the relationship.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface JsonApiLookupIncludeAutomatically {
}
