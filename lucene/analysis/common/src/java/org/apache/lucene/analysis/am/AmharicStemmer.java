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

public class AmharicStemmer {

  private static final char[][] UNICODECHARS = { 
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

  public int stem(char s[], int len) {
    if (endsWith(s, len, "ና")) { // 
      len = deleteN(s, len-1, len, 1);
    }

    int last = len - 1;

    if (last > 6 && // 5 SUFFIXES
        ((endsWith(s, len, "አችኋለሁ")) ||
        (endsWith(s, len, "አችዋለሁ")) ||
        (endsWith(s, len, "አችዋለሽ")))) {
      last -= 5;
      len = deleteN(s, last + 1, len, 5);
    } else if (last > 5 && // 4 SUFFIXES
              ((endsWith(s, len, "ችሁአት")) ||
              (endsWith(s, len, "ዋችኋል")) ||
              (endsWith(s, len, "ዋቸዋል")) ||
              (endsWith(s, len, "ቸዋለች")) ||
              (endsWith(s, len, "አታለሁ")) ||
              (endsWith(s, len, "አታለሽ")) ||
              (endsWith(s, len, "ሃቸዋል")) ||
              (endsWith(s, len, "ቸዋለህ")) ||
              (endsWith(s, len, "ሻአቸው")) ||
              (endsWith(s, len, "ሻቸዋል")))) {    
      last -= 4;
      len = deleteN(s, last, len, 4);
    }
    else if (last > 3 && // 3 SUFFIXES
            ((endsWith(s, len, "ሽኛል")) ||
            (endsWith(s, len, "ሽዋል")) ||
            (endsWith(s, len, "ሻታል")) ||
            (endsWith(s, len, "ሽናል")) ||
            (endsWith(s, len, "ሻቸው")) ||
            (endsWith(s, len, "ኛለሽ")) ||
            (endsWith(s, len, "ዋለሽ")) ||
            (endsWith(s, len, "ናለሽ")) ||
            (endsWith(s, len, "ኋጨው")) ||
            (endsWith(s, len, "ኸኛል")) ||
            (endsWith(s, len, "ኸዋል")) ||
            (endsWith(s, len, "ሃተል")) ||
            (endsWith(s, len, "ኸናል")) ||
            (endsWith(s, len, "ኛለህ")) ||
            (endsWith(s, len, "ዋለህ")) ||
            (endsWith(s, len, "ታለህ")) ||
            (endsWith(s, len, "ናለህ")) ||
            (endsWith(s, len, "ኋችሁ")) ||
            (endsWith(s, len, "ኋቸው")) ||
            (endsWith(s, len, "ሃለሁ")) ||
            (endsWith(s, len, "ሻለሁ")) ||
            (endsWith(s, len, "ዋለሁ")) ||
            (endsWith(s, len, "ቻችሁ")) ||
            (endsWith(s, len, "ቻችው")) ||
            (endsWith(s, len, "ናችሁ")) ||
            (endsWith(s, len, "ናቸው")) ||
            (endsWith(s, len, "ችሁኝ")) ||
            (endsWith(s, len, "ችሁት")) ||
            (endsWith(s, len, "ችሁን")) ||
            (endsWith(s, len, "ኘለች")) ||
            (endsWith(s, len, "ሃለች")) ||
            (endsWith(s, len, "ሻለች")) ||
            (endsWith(s, len, "ዋለች")) ||
            (endsWith(s, len, "ታለች")) ||
            (endsWith(s, len, "ናለች")) ||
            (endsWith(s, len, "ንሃል")) ||
            (endsWith(s, len, "ንሻል")) ||
            (endsWith(s, len, "ነዋል")) ||
            (endsWith(s, len, "ናታል")) ||
            (endsWith(s, len, "ውኛል")) ||
            (endsWith(s, len, "ውሃል")) ||
            (endsWith(s, len, "ውሻል")) ||
            (endsWith(s, len, "ውታል")) ||
            (endsWith(s, len, "ዋታል")) ||
            (endsWith(s, len, "ውናል")) ||
            (endsWith(s, len, "ዋችሁ")) ||
            (endsWith(s, len, "ዋችው")) ||
            (endsWith(s, len, "ውያን")))){
        last -= 3;
        len = deleteN(s, last, len, 3);
    }
    else if (last > 2 && // 2 SUFFIXES
            ((endsWith(s, len, "ሽኝ")) ||
            (endsWith(s, len, "ሽው")) ||
            (endsWith(s, len, "ሻት")) ||
            (endsWith(s, len, "ሽን")) ||
            (endsWith(s, len, "ኛል")) ||
            (endsWith(s, len, "ሃል")) ||
            (endsWith(s, len, "ሻል")) ||
            (endsWith(s, len, "ታል")) ||
            (endsWith(s, len, "ናል")) ||
            (endsWith(s, len, "ችሁ")) ||
            (endsWith(s, len, "ቸሁ")) ||
            (endsWith(s, len, "ኸኝ")) ||
            (endsWith(s, len, "ኸው")) ||
            (endsWith(s, len, "ሃት")) ||
            (endsWith(s, len, "ኸን")) ||
            (endsWith(s, len, "ሁህ")) ||
            (endsWith(s, len, "ሁሽ")) ||
            (endsWith(s, len, "ሁት")) ||
            (endsWith(s, len, "ኋት")) ||
            (endsWith(s, len, "ችኝ")) ||
            (endsWith(s, len, "ችህ")) ||
            (endsWith(s, len, "ችሽ")) ||
            (endsWith(s, len, "ችው")) ||
            (endsWith(s, len, "ቻት")) ||
            (endsWith(s, len, "ችን")) ||
            (endsWith(s, len, "ንህ")) ||
            (endsWith(s, len, "ንሽ")) ||
            (endsWith(s, len, "ነው")) ||
            (endsWith(s, len, "ናት")) ||
            (endsWith(s, len, "ውኝ")) ||
            (endsWith(s, len, "ውህ")) ||
            (endsWith(s, len, "ውሽ")) ||
            (endsWith(s, len, "ውት")) ||
            (endsWith(s, len, "ዋት")) ||
            (endsWith(s, len, "ዎች")) ||
            (endsWith(s, len, "ያን")) ||
            (endsWith(s, len, "ያት")) ||
            (endsWith(s, len, "ነት")))) {
      last -= 2;
      len = deleteN(s, last, len, 2);
    } else if ((last>1 && s[last]==NYE) || // 1 SUFFIX
            (last>1 && s[last]==HE) || 
            (last>1 && s[last]==SHE) ||
            (s[last]==WE) ||
            (s[last]==NE) ||
            (last>1 && s[last]==WI) ||
            (last>1 && s[last]==CE) ||
            (last>1 && s[last]==LE) ||
            (s[last]==TE) || // "ት"
            (s[last]==ME)) { // "እኔም"
      last -= 1;
      len = deleteN(s, last, len, 1);
    }

    // step 4
    int l = 0;

    if ((endsWith(s, len, "እንድ")) ||
       (endsWith(s, len, "እንደ")) ||
       (endsWith(s, len, "እንዲ")) ||
       (endsWith(s, len, "የማይ"))) {
      len = deleteN(s, l, len, 3);
      l+=3;
    } else if ((endsWith(s, len, "እን")) ||
              (endsWith(s, len, "እነ")) ||
              (endsWith(s, len, "እየ")) ||
              (endsWith(s, len, "አል")) ||
              (endsWith(s, len, "የሚ")) ||
              (endsWith(s, len, "የም")) ||
              (endsWith(s, len, "አይ")) ||
              (endsWith(s, len, "አስ")) ||
              (endsWith(s, len, "በመ")) ||
              (endsWith(s, len, "የተ"))) {
      len = deleteN(s, l, len, 2);
      l+=2;
    } 
    else if ((endsWith(s, len, "እ")) ||
            (endsWith(s, len, "ል")) ||
            (endsWith(s, len, "ት")) ||
            (endsWith(s, len, "ይ")) ||
            (endsWith(s, len, "ብ")) ||
            (endsWith(s, len, "በ")) ||
            (endsWith(s, len, "ቢ")) ||
            (endsWith(s, len, "ከ")) ||
            (endsWith(s, len, "ለ")) ||
            (endsWith(s, len, "ያ")) ||
            (endsWith(s, len, "የ")) ||
            (endsWith(s, len, "ል"))) {
      len = deleteN(s, l, len, 1);        
      l+=1;
    }


    boolean found;
    for(int w = l; w <= last; w++){
      found = false;
      for(int i=0; i < UNICODECHARS.length; i++){
          for(int j=0; j < UNICODECHARS[i].length; j++){
              if(s[w] == UNICODECHARS[i][j]) {
                  s[w] = UNICODECHARS[i][5];
                  found = true;
                  break;
              }
              if(found) break;
          }
      }
    }
/*
    // k = last
    int le = last - l + 1;
    boolean repeated=true;

    for(int i = l; i < last; i++) {
      if(s[i] == s[i+1]) {
        for(int j=i+1; j < last; j++) s[j] = s[j+1];
        last--;
        break;
      }
    }
*/
    return len;
  }
}
