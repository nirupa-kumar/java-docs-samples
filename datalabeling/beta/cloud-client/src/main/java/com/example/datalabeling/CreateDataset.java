/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.datalabeling;

// [START datalabeling_create_dataset_beta]
import com.google.cloud.datalabeling.v1beta1.CreateDatasetRequest;
import com.google.cloud.datalabeling.v1beta1.DataLabelingServiceClient;
import com.google.cloud.datalabeling.v1beta1.Dataset;
import java.io.IOException;

class CreateDataset {

  // Create a dataset that is initially empty.
  static void createDataset(String projectId) {
    // String projectId = "YOUR_PROJECT_ID";

    try (DataLabelingServiceClient dataLabelingServiceClient = DataLabelingServiceClient.create()) {
      String formattedProjectName = DataLabelingServiceClient.formatProjectName(projectId);

      Dataset dataset = Dataset.newBuilder()
          .setDisplayName("YOUR_DATASET_DISPLAY_NAME")
          .setDescription("YOUR_DESCRIPTION")
          .build();

      CreateDatasetRequest createDatasetRequest = CreateDatasetRequest.newBuilder()
          .setParent(formattedProjectName)
          .setDataset(dataset)
          .build();

      Dataset createdDataset = dataLabelingServiceClient.createDataset(createDatasetRequest);

      System.out.format("Name: %s\n", createdDataset.getName());
      System.out.format("DisplayName: %s\n", createdDataset.getDisplayName());
      System.out.format("Description: %s\n", createdDataset.getDescription());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
// [END datalabeling_create_dataset_beta]
