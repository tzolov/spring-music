/*
 * Copyright 2023-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.samples.music.config.ai;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.loader.impl.JsonLoader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;

/**
 *
 * @author Christian Tzolov
 */
public class VectorStoreInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private VectorStore vectorStore;

	@Value("classpath:/albums.json")
	private Resource albumsResource;

	public VectorStoreInitializer(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		JsonLoader jsonLoader = new JsonLoader(this.albumsResource,
				"artist", "title", "releaseYear", "genre");
		List<Document> documents = jsonLoader.load();
		this.vectorStore.add(documents);
	}

}
