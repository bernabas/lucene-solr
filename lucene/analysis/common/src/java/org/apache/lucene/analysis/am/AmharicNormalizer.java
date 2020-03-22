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


import static org.apache.lucene.analysis.util.StemmerUtil.*;

/**
 *  Normalizer for Amharic.
 *  <p>
 *  Normalization is done in-place for efficiency, operating on a termbuffer.
 *  <p>
 *  Normalization is defined as:
 *  <ul>
 *  <li> Normalization of words that use different alphabest but have the same pronunciation.
 * </ul>
 *
 */

public class AmharicNormalizer {

    /**
     * Normalize an input buffer of Amharic text
     *
     * @param s input buffer
     * @param len length of input buffer
     * @return length of input buffer after normalization
     */
    public int normalize(char s[], int len) {
        for(int i = 0; i < len; i++) {
          switch(s[i]) {
            // (ሐ or ኀ) to ሀ
            case 'ሐ':
            case 'ኀ':
              s[i] = 'ሀ';
              break;
            case 'ሑ':
            case 'ኁ':
              s[i] = 'ሁ';
              break;
            case 'ሒ':
            case 'ኂ':
              s[i] = 'ሂ';
              break;
            case 'ሓ':
            case 'ኃ':
              s[i] = 'ሃ';
              break;
            case 'ሔ':
            case 'ኄ':
              s[i] = 'ሄ';
              break;
            case 'ሕ':
            case 'ኅ':
              s[i] = 'ህ';
              break;
            case 'ሖ':
            case 'ኆ':
              s[i] = 'ሆ';
              break;  
            // ሠ to ሰ
            case 'ሠ':
              s[i] = 'ሰ';
              break;
            case 'ሡ':
              s[i] = 'ሱ';
              break;
            case 'ሢ':
              s[i] = 'ሲ';
              break; 
            case 'ሣ':
              s[i] = 'ሳ';
              break;
            case 'ሤ':
              s[i] = 'ሴ';
              break;
            case 'ሥ':
              s[i] = 'ስ';
              break;
            case 'ሦ':
              s[i] = 'ሶ';
              break;        
            // አ to ዐ   
            case 'አ':
              s[i] = 'ዐ';
              break;
            case 'ኡ':
              s[i] = 'ዑ';
              break;
            case 'ኢ':
              s[i] = 'ዒ';
              break;
            case 'ኣ':
              s[i] = 'ዓ';
              break;
            case 'ኤ':
              s[i] = 'ዔ';
              break;
            case 'ኧ':
              s[i] = 'ዕ';
              break;
            case 'ኦ':
              s[i] = 'ዕ';
              break;
            // ጸ to ፀ
            case 'ጸ':
              s[i] = 'ፀ';
              break;
            case 'ጹ':
              s[i] = 'ፁ';
              break;
            case 'ጺ':
              s[i] = 'ፂ';
              break;
            case 'ጻ':
              s[i] = 'ፃ';
              break;
            case 'ጼ':
              s[i] = 'ፄ';
              break;
            case 'ጽ':
              s[i] = 'ፅ';
              break;
            case 'ጾ':
              s[i] = 'ፆ';
              break;    
          } 
        }
        return len;
    }
}
