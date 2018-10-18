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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.util.TestUtil;

import java.io.IOException;

public class TestAmharicNormalizer extends BaseTokenStreamTestCase {
  public void testBasicWords() throws IOException {
    check("ጸሀየ", "ፀሀየ");
    check("ሠሀየ", "ሰሀየ");
    check("ሀይማኖት", "ሀይማኖት");
    check("ሡቅ", "ሱቅ");
  }

  private void check(String input, String output) throws IOException {
    Tokenizer tokenizer = whitespaceMockTokenizer(input);
    TokenFilter tf = new AmharicNormalizationFilter(tokenizer);
    assertTokenStreamContents(tf, new String[] { output });
  }
}
