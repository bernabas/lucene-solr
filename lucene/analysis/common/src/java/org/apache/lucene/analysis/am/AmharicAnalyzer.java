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
import java.io.Reader;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.DecimalDigitFilter;
import org.apache.lucene.analysis.in.IndicNormalizationFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Analyzer for Amharic.
 */
public final class AmharicAnalyzer extends StopwordAnalyzerBase {
  public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";

  public static CharArraySet getDefaultStopSet(){
    return DefaultSetHolder.DEFAULT_STOP_SET;
  }
  private static class DefaultSetHolder {
    static final CharArraySet DEFAULT_STOP_SET;

    static {
      try {
        DEFAULT_STOP_SET = loadStopwordSet(false, AmharicAnalyzer.class, DEFAULT_STOPWORD_FILE, "#");
      } catch (IOException ex) {
        // default set should always be present as it is part of the
        // distribution (JAR)
        throw new RuntimeException("Unable to load default stopword set");
      }
    }
  }

  private final CharArraySet stemExclusionSet;

  public AmharicAnalyzer() {
      this(DefaultSetHolder.DEFAULT_STOP_SET);
  }

  public AmharicAnalyzer(CharArraySet stopwords){
      this(stopwords, CharArraySet.EMPTY_SET);
  }

  public AmharicAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet){
      super(stopwords);
      this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
  }

  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
      final Tokenizer source = new StandardTokenizer();
      TokenStream result = new LowerCaseFilter(source);
      result = new DecimalDigitFilter(result);
      result = new StopFilter(result, stopwords);
      result = new AmharicNormalizationFilter(result);
      if(!stemExclusionSet.isEmpty()) {
          result = new SetKeywordMarkerFilter(result, stemExclusionSet);
      }
      return new TokenStreamComponents(source, new AmharicStemFilter(result));
  }

  @Override
  protected TokenStream normalize(String fieldName, TokenStream in) {
      TokenStream result = new LowerCaseFilter(in);
      result = new DecimalDigitFilter(result);
      result = new AmharicNormalizationFilter(result);
      return result;
  }
}
