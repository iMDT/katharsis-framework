package io.katharsis.client.module;

import io.katharsis.module.Module;
import io.katharsis.repository.information.internal.DefaultRelationshipRepositoryInformationBuilder;
import io.katharsis.repository.information.internal.DefaultResourceRepositoryInformationBuilder;
import io.katharsis.resource.field.ResourceFieldNameTransformer;
import io.katharsis.resource.information.AnnotationResourceInformationBuilder;

public class ClientModule implements Module {

	@Override
	public String getModuleName() {
		return "client";
	}

	@Override
	public void setupModule(ModuleContext context) {
		context.addResourceInformationBuilder(new AnnotationResourceInformationBuilder(new ResourceFieldNameTransformer()));
		context.addRepositoryInformationBuilder(new DefaultResourceRepositoryInformationBuilder());
		context.addRepositoryInformationBuilder(new DefaultRelationshipRepositoryInformationBuilder());
	}

}
