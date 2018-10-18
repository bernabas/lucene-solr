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

/**
 * Test the Amharic Analyzer
 *
 */
public class TestAmharicAnalyzer extends BaseTokenStreamTestCase {
  
  /** This test fails with NPE when the 
   * stopwords file is missing in classpath */
  public void testResourcesAvailable() {
    new AmharicAnalyzer().close();
  }

  /**
   * Non-amharic text gets treated in a similar way as SimpleAnalyzer.
   */
  public void testEnglishInput() throws Exception {
    AmharicAnalyzer a = new AmharicAnalyzer();
    assertAnalyzesTo(a, "English text.", new String[] {
        "english", "text" });
    a.close();
  }

  /**
   * Test that default stopwords work.
   */
  public void testDefaultStopwords() throws Exception {
    AmharicAnalyzer a = new AmharicAnalyzer();
    assertAnalyzesTo(a, "ግን ይህ ረቂቅ ነው", new String[] {
        "በጣም", "ረቂቅ" });
    a.close();
  }

    /**
   * Test that it ignores ፣ and ።
   */
  public void testIgnorsMarks() throws Exception {
    AmharicAnalyzer a = new AmharicAnalyzer();
    assertAnalyzesTo(a, "ግን። ይህ በጣም፣ረቂቅ። ነው ።", new String[] {
        "በጣም", "ረቂቅ" });
    a.close();
  }
  
  /**
   * Test that custom stopwords work.
   */
  public void testCustomStopwords() throws Exception {
    CharArraySet set = new CharArraySet(asSet("የእነሱ"), false);
    AmharicAnalyzer a = new AmharicAnalyzer(set);
    assertAnalyzesTo(a, "የእነሱ ሀዲስ ዓለማየሁ", new String[] { "ሀዲስ",
        "ኣለማየሁ" });
    a.close();
  }

  /** blast some random strings through the analyzer */
  public void testRandomStrings() throws Exception {
    Analyzer analyzer = new AmharicAnalyzer();
    checkRandomData(random(), analyzer, 1000*RANDOM_MULTIPLIER);
    analyzer.close();
  }
}
