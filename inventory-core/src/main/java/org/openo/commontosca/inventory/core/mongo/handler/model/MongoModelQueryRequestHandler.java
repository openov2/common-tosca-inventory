/**
 * Copyright  2017 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openo.commontosca.inventory.core.mongo.handler.model;

import java.util.Collections;

import org.bson.Document;
import org.openo.commontosca.inventory.core.Constants.ModelKey;
import org.openo.commontosca.inventory.core.model.Model;
import org.openo.commontosca.inventory.core.mongo.MongoQueryResult;
import org.openo.commontosca.inventory.core.mongo.handler.AbstractMongoInventoryRequestHandler;
import org.openo.commontosca.inventory.sdk.api.data.ValueMap;
import org.openo.commontosca.inventory.sdk.api.deferred.SimpleDeferred;
import org.openo.commontosca.inventory.sdk.api.request.InventoryModelRequest.Query;
import org.openo.commontosca.inventory.sdk.api.result.QueryResult;
import org.openo.commontosca.inventory.sdk.support.DeferredResponse;
import org.openo.commontosca.inventory.sdk.support.result.HeapQueryResult;

import com.mongodb.async.client.FindIterable;

public class MongoModelQueryRequestHandler
    extends AbstractMongoInventoryRequestHandler<Query, QueryResult> {

  @Override
  public SimpleDeferred<QueryResult> handle(Query request) {
    DeferredResponse<QueryResult> response = new DeferredResponse<>();
    FindIterable<Document> find =
        this.getDatabase(request).getCollection(Model.MODEL_DEFAULT_COLLECTION_NAME).find();
    ValueMap filter = new ValueMap();
    if (request.getModelName() != null) {
      filter.put(ModelKey.NAME, request.getModelName());
    }
    find.filter(new Document(filter));
    find.map(document -> ValueMap.wrap(document)).batchCursor((cursor, ex) -> {
      if (ex != null) {
        response.reject(ex);
      } else if (cursor != null) {
        response.resolve(new MongoQueryResult(cursor));
      } else {
        response.resolve(new HeapQueryResult(Collections.emptyList()));
      }
    });
    return response;
  }

}
