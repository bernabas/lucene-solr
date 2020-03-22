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

import static org.apache.lucene.analysis.util.StemmerUtil.deleteN;
import static org.apache.lucene.analysis.util.StemmerUtil.endsWith;
import static org.apache.lucene.analysis.util.StemmerUtil.startsWith;
import static org.apache.lucene.analysis.util.StemmerUtil.delete;

import java.util.ArrayList;
import java.util.Arrays;

public class AmharicStemmer {
  private static final char[][] AMHARIC_CHARS = { 
    {'ሀ', 'ሁ','ሂ','ሃ','ሄ','ህ','ሆ'},
    {'ለ', 'ሉ','ሊ','ላ','ሌ','ል','ሎ'},
    {'ሐ', 'ሑ','ሒ','ሓ','ሔ','ሕ','ሖ'},
    {'መ', 'ሙ','ሚ','ማ','ሜ','ም','ሞ'},
    {'ሠ', 'ሡ','ሢ','ሣ','ሤ','ሥ','ሦ'},
    {'ረ', 'ሩ','ሪ','ራ','ሬ','ር','ሮ'},
    {'ሰ', 'ሱ','ሲ','ሳ','ሴ','ስ','ሶ'},
    {'ሸ', 'ሹ','ሺ','ሻ','ሼ','ሽ','ሾ'},
    {'ቀ', 'ቁ','ቂ','ቃ','ቄ','ቅ','ቆ'},
    {'በ', 'ቡ','ቢ','ባ','ቤ','ብ','ቦ'},
    {'ቨ', 'ቩ','ቪ','ቫ','ቬ','ቭ','ቮ'},
    {'ተ', 'ቱ','ቲ','ታ','ቴ','ት','ቶ'},
    {'ቸ', 'ቹ','ቺ','ቻ','ቼ','ች','ቾ'},
    {'ኀ', 'ኁ','ኂ','ኃ','ኄ','ኅ','ኆ'},
    {'ነ', 'ኑ','ኒ','ና','ኔ','ን','ኖ'},
    {'ኘ', 'ኙ','ኚ','ኛ','ኜ','ኝ','ኞ'},
    {'አ', 'ኡ','ኢ','ኣ','ኤ','እ','ኦ'},
    {'ከ', 'ኩ','ኪ','ካ','ኬ','ክ','ኮ'},
    {'ኸ', 'ኹ','ኺ','ኻ','ኼ','ኽ','ኾ'},
    {'ወ', 'ዉ','ዊ','ዋ','ዌ','ው','ዎ'},
    {'ዐ', 'ዑ','ዒ','ዓ','ዔ','ዕ','ዖ'},
    {'ዘ', 'ዙ','ዚ','ዛ','ዜ','ዝ','ዞ'},
    {'ዠ', 'ዡ','ዢ','ዣ','ዤ','ዥ','ዦ'},
    {'የ', 'ዩ','ዪ','ያ','ዬ','ይ','ዮ'},
    {'ደ', 'ዱ','ዲ','ዳ','ዴ','ድ','ዶ'},
    {'ዸ', 'ዹ','ዺ','ዻ','ዼ','ዽ','ዾ'},
    {'ጀ', 'ጁ','ጂ','ጃ','ጄ','ጅ','ጆ'},
    {'ገ', 'ጉ','ጊ','ጋ','ጌ','ግ','ጎ'},
    {'ጠ', 'ጡ','ጢ','ጣ','ጤ','ጥ','ጦ'},
    {'ጨ', 'ጩ','ጪ','ጫ','ጬ','ጭ','ጮ'},
    {'ፀ', 'ፁ','ፂ','ፃ','ፄ','ፅ','ፆ'},
    {'ፈ', 'ፉ','ፊ','ፋ','ፌ','ፍ','ፎ'},
    {'ፐ', 'ፑ','ፒ','ፓ','ፔ','ፕ','ፖ'}
  };

  private static final char[] VOWELS = { 'ኧ', 'ኡ', 'ኢ', 'ኣ', 'ኤ', 'እ', 'ኦ' }; 

  // suffix list
  private static final String[] SUFFIX = {
    "ኢዕኧልኧሽ", "ኣውኢው", "ኣችኧውኣል", "ኧችኣት", "ኧችኣችህኡ", 
    "ኧችኣችኧው", "ኣልኧህኡ", "ኣውኦች", "ኣልኧህ", "ኣልኧሽ", 
    "ኣልችህኡ", "ኣልኣልኧች", "ብኣችኧውስ", "ብኣችኧው", "ኣችኧውን", 
    "ኣልኧች", "ኣልኧን", "ኣልኣችህኡ", "ኣችህኡን", "ኣችህኡ", "ኣችህኡት",
    "ውኦችንንኣ", "ውኦችን", "ኣችኧው", "ውኦችኡን", "ውኦችኡ", 
    "ኧውንኣ", "ኦችኡን", "ኦውኦች", "ኧኝኣንኧትም", "ኧኝኣንኣ", "ኧኝኣንኧት",
    "ኧኝኣን", "ኧኝኣውም", "ኧኝኣው", "ኝኣውኣ", "ብኧትን", "ኣችህኡም",
    "ኦውኣ", "ኧችው", "ኧችኡ", "ኤችኡ", "ንኧው", "ንኧት", "ኣልኡ",
    "ኣችን", "ክኡም", "ክኡት", "ክኧው", "ኧችን", "ኧችም", "ኧችህ", 
    "ኧችሽ", "ኧችን", "ኧችው", "ይኡሽን", "ይኡሽ", "ኧውኢ", "ኦችንንኣ",
    "ኣውኢ", "ብኧት", "ኦች", "ኦችኡ", "ውኦን", "ኧኝኣ", "ኝኣውን", "ኝኣው",
    "ኦችን", "ኣል", "ኧም", "ሽው", "\nክም", "ኧው", "ትም", "ውኦ",
    "ውም", "ውን", "ንም", "ሽን", "ኣች", "ኡት", "ኢት", "ክኡ", "ኤ",
    "ህ", "ሽ", "ኡ", "ሽ", "ክ", "ኧ", "ኧች", "ኡን", "ን", "ም","ንኣ", "ው"
  };

  // prefix list
  private static final String[] PREFIX = {
    "ስልኧምኣይ", "ይኧምኣት", "ዕንድኧ", "ይኧትኧ", "ብኧምኣ", "ብኧትኧ",
    "ዕኧል", "ስልኧ", "ምኧስ", "ዕይኧ", "ዕኧስ", "ዕኧት", "ዕኧን", "ዕኧይ",
    "ይኣል", "ስኣት", "ስኣን", "ስኣይ", "ስኣል", "ይኣስ", "ይኧ", "ልኧ",
    "ክኧ", "እን", "ዕን", "ዐል", "ይ", "ት", "አ", "እ"
  };

  public int stem(char s[], int len) {

    // build a consonant-vowel form
    // ArrayList<Character> cvForm = new ArrayList<Character>();
    String cvForm = "";
    for(int x = 0; x < len; x++){
      char a = s[x];
      for(int i = 0; i < AMHARIC_CHARS.length; i++) {
        for(int j = 0; j < AMHARIC_CHARS[i].length; j++) {
          if(a == AMHARIC_CHARS[i][j]) {
             if(j == 5) {
              // no need to add a vowel
              s[x] = a;
              cvForm += a;
             } else {
              // find vowel
              cvForm += AMHARIC_CHARS[i][5];
              cvForm += VOWELS[j];
             }
          }
        }
      }
    }
    char [] word = cvForm.toCharArray();

    int wordLength = word.length;
    if (word.length > 3) {
      // remove suffix
      for(int i = 0; i < SUFFIX.length; i++) {
        String suffix = SUFFIX[i];
        if (endsWith(word, wordLength, suffix)) {
          wordLength = deleteN(word, wordLength - suffix.length(), wordLength, suffix.length());
          break;
        }
      }

      // remove prefix
      for(int i = 0; i < PREFIX.length; i++) {
        String prefix = PREFIX[i];
        if (startsWith(word, wordLength, prefix)) {
          wordLength = deleteN(word, 0, wordLength, prefix.length());
          break;
        }
      }
    }

    // word stem
    for(int i = 0; i < wordLength; i++) {
      for(int j = 0; j < VOWELS.length; i++) {
        if (word[i] == VOWELS[j]) {
          wordLength = delete(word, i, wordLength);
        }
      }
    }

    // copy over change to s
    System.arraycopy(word, 0, s, 0, wordLength);
    len = wordLength;
    return len;
  }
}
