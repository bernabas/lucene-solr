/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.analysis.am;


import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.MockTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;

/**
 * Test the Amharic Normalization Filter
 *
 */
public class TestAmharicStemFilter extends BaseTokenStreamTestCase {
  public void testNAASuffix() throws IOException {
    //check("መምጣትና", "ምትት"); 
    //check("መምታትና", "ምትት");
    check("መታኝ", "ምኝ");
  }

  public void testFiveSuffix() throws IOException {
    check("ፈልጊአችኋለሁ", "ፍልግ");
    check("ፈልጊአችዋለሁ", "ፍልግ");
    check("ፈልጊአችዋለሽ", "ፍልግ");
  }

  private void check(final String input, final String expected) throws IOException {
    MockTokenizer tokenStream  = whitespaceMockTokenizer(input);
    AmharicStemFilter filter = new AmharicStemFilter(tokenStream);
    assertTokenStreamContents(filter, new String[]{expected});
  }
}
