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

import static org.junit.Assert.assertThat;

import com.google.cloud.datalabeling.v1beta1.DataLabelingServiceClient;
import com.google.cloud.datalabeling.v1beta1.DataLabelingServiceClient.ListDatasetsPagedResponse;
import com.google.cloud.datalabeling.v1beta1.Dataset;
import com.google.cloud.datalabeling.v1beta1.ListDatasetsRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Integration (system) tests for {@link CreateDataset}.
 */
@RunWith(JUnit4.class)
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public class CreateDatasetIT {

  private ByteArrayOutputStream bout;

  private static String PROJECT_ID = System.getenv().get("GOOGLE_CLOUD_PROJECT");

  @Before
  public void setUp() {
    bout = new ByteArrayOutputStream();
    System.setOut(new PrintStream(bout));
  }

  @After
  public void tearDown() {
    System.setOut(null);
    bout.reset();

    // Delete the Dataset
    try (DataLabelingServiceClient dataLabelingServiceClient = DataLabelingServiceClient.create()) {
      ListDatasetsRequest listRequest = ListDatasetsRequest.newBuilder()
          .setParent(DataLabelingServiceClient.formatProjectName(PROJECT_ID))
          .build();

      ListDatasetsPagedResponse response = dataLabelingServiceClient
          .listDatasets(listRequest);

      for (Dataset dataset : response.getPage().iterateAll()) {
        if (dataset.getDisplayName().equals("YOUR_DATASET_DISPLAY_NAME")) {
          dataLabelingServiceClient.deleteDataset(dataset.getName());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateDataset() {
    CreateDataset.createDataset(PROJECT_ID);

    String output = bout.toString();

    assertThat(output, CoreMatchers.containsString(
        "DisplayName: YOUR_DATASET_DISPLAY_NAME"));
    assertThat(output, CoreMatchers.containsString("Description: YOUR_DESCRIPTION"));
  }
}
