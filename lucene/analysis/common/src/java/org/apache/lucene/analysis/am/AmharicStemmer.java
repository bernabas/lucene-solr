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

  private static char HA = 'ሀ';
  private static char HU= 'ሁ'; // ሁ
  private static char HI ='ሂ';
  private static char HAA ='ሃ';
  private static char HEE ='ሄ';
  private static char HE ='ህ';
  private static char HO ='ሆ';
  private static char LA ='ለ';
  private static char LU ='ሉ';
  private static char LI ='ሊ';
  private static char LAA ='ላ';
  private static char LEE ='ሌ';
  private static char LE ='ል';
  private static char LO ='ሎ';
  private static char HHA ='ሐ';
  private static char HHU= 'ሑ';
  private static char HHI ='ሒ';
  private static char HHAA ='ሓ';
  private static char HHEE ='ሔ';
  private static char HHE ='ሕ';
  private static char HHO ='ሖ';
  private static char MA = 'መ';
  private static char MU ='ሙ';
  private static char MI ='ሚ';
  private static char MAA ='ማ';
  private static char MEE ='ሜ';
  private static char ME ='ም';
  private static char MO ='ሞ';
  private static char SZA='ሠ';
  private static char SZU='ሡ';
  private static char SZI='ሢ';
  private static char SZAA='ሣ';
  private static char SZEE='ሤ';
  private static char SZE='ሥ';
  private static char SZO='ሦ';
  private static char RA='ረ';
  private static char RU='ሩ';
  private static char RI='ሪ';
  private static char RAA='ራ';
  private static char REE='ሬ';
  private static char RE='ር';
  private static char RO='ሮ';
  private static char SA='ሰ';
  private static char SU='ሱ';
  private static char SI='ሲ';
  private static char SAA='ሳ';
  private static char SEE='ሴ';
  private static char SE='ስ';
  private static char SO='ሶ';
  private static char SHA='ሸ';
  private static char SHU='ሹ';
  private static char SHI='ሺ';
  private static char SHAA='ሻ';
  private static char SHEE='ሼ';
  private static char SHE='ሽ';
  private static char SHO='ሾ';
  private static char QA='ቀ';
  private static char QU='ቁ';
  private static char QI='ቂ';
  private static char QAA='ቃ';
  private static char QEE='ቄ';
  private static char QE='ቅ';
  private static char QO='ቆ';
  private static char BA='በ';
  private static char BU='ቡ';
  private static char BI='ቢ';
  private static char BAA='ባ';
  private static char BEE='ቤ';
  private static char BE='ብ';
  private static char BO='ቦ';
  private static char VA='ቨ';
  private static char VU='ቩ';
  private static char VI='ቪ';
  private static char VAA='ቫ';
  private static char VEE='ቬ';
  private static char VE='ቭ';
  private static char VO='ቮ';
  private static char TA='ተ';
  private static char TU='ቱ';
  private static char TI='ቲ';
  private static char TAA='ታ';
  private static char TEE='ቴ';
  private static char TE='ት';
  private static char TO='ቶ';
  private static char CA='ቸ';
  private static char CU='ቹ';
  private static char CI='ቺ';
  private static char CAA='ቻ';
  private static char CEE='ቼ';
  private static char CE='ች';
  private static char CO='ቾ';
  private static char XA='ኀ';
  private static char XU='ኁ';
  private static char XI='ኂ';
  private static char XAA='ኃ';
  private static char XEE='ኄ';
  private static char XE='ኅ';
  private static char XWAA='ኋ';
  private static char XO='ኆ';
  private static char NA='ነ';
  private static char NU='ኑ';
  private static char NI='ኒ';
  private static char NAA='ና';
  private static char NEE='ኔ';
  private static char NE='ን';
  private static char NO='ኖ';
  private static char NYA='ኘ';
  private static char NYU='ኙ';
  private static char NYI='ኚ';
  private static char NYAA='ኛ';
  private static char NYEE='ኜ';
  private static char NYE='ኝ';
  private static char NYO='ኞ';
  private static char A='አ';
  private static char U='ኡ';
  private static char I='ኢ';
  private static char AA='ኣ';
  private static char EE='ኤ';
  private static char E='እ';
  private static char O='ኦ';
  private static char KA='ከ';
  private static char KU='ኩ';
  private static char KI='ኪ';
  private static char KAA='ካ';
  private static char KEE='ኬ';
  private static char KE='ክ';
  private static char KO='ኮ';
  private static char KXA='ኸ';
  private static char KXU='ኹ';
  private static char KXI='ኺ';
  private static char KXAA='ኻ';
  private static char KXEE='ኼ';
  private static char KXE='ኽ';
  private static char KXO='ኾ';
  private static char WA='ወ';
  private static char WU='ዉ';
  private static char WI='ዊ';
  private static char WAA='ዋ';
  private static char WEE='ዌ';
  private static char WE='ው';
  private static char WO='ዎ';
  private static char A2='ዐ';
  private static char U2='ዑ';
  private static char I2='ዒ';
  private static char AA2='ዓ';
  private static char EE2='ዔ';
  private static char E2='ዕ';
  private static char O2='ዖ';
  private static char ZA='ዘ';
  private static char ZU='ዙ';
  private static char ZI='ዚ';
  private static char ZAA='ዛ';
  private static char ZEE='ዜ';
  private static char ZE='ዝ';
  private static char ZO='ዞ';
  private static char ZHA='ዠ';
  private static char ZHU='ዡ';
  private static char ZHI='ዢ';
  private static char ZHAA='ዣ';
  private static char ZHEE='ዤ';
  private static char ZHE='ዥ';
  private static char ZHO='ዦ';
  private static char YA='የ';
  private static char YU='ዩ';
  private static char YI='ዪ';
  private static char YAA='ያ';
  private static char YEE='ዬ';
  private static char YE='ይ';
  private static char YO='ዮ';
  private static char DA='ደ';
  private static char DU='ዱ';
  private static char DI='ዲ';
  private static char DAA='ዳ';
  private static char DEE='ዴ';
  private static char DE='ድ';
  private static char DO='ዶ';
  private static char JA='ጀ';
  private static char JU='ጁ';
  private static char JI='ጂ';
  private static char JAA='ጃ';
  private static char JEE='ጄ';
  private static char JE='ጅ';
  private static char JO='ጆ';
  private static char GA='ገ';
  private static char GU='ጉ';
  private static char GI='ጊ';
  private static char GAA='ጋ';
  private static char GEE='ጌ';
  private static char GE='ግ';
  private static char GO='ጎ';
  private static char THA='ጠ';
  private static char THU='ጡ';
  private static char THI='ጢ';
  private static char THAA='ጣ';
  private static char THEE='ጤ';
  private static char THE='ጥ';
  private static char THO='ጦ';
  private static char CHA='ጨ';
  private static char CHU= 'ጩ';
  private static char CHI='ጪ';
  private static char CHAA='ጫ';
  private static char CHEE='ጬ';
  private static char CHE='ጭ';
  private static char CHO='ጮ';
  private static char TZA='ፀ';
  private static char TZU='ፁ';
  private static char TZI='ፂ';
  private static char TZAA='ፃ';
  private static char TZEE='ፄ';
  private static char TZE='ፅ';
  private static char TZO='ፆ';
  private static char FA='ፈ';
  private static char FU='ፉ';
  private static char FI='ፊ';
  private static char FAA='ፋ';
  private static char FEE='ፌ';
  private static char FE='ፍ';
  private static char FO='ፎ';
  private static char PA='ፐ';
  private static char PU='ፑ';
  private static char PI='ፒ';
  private static char PAA='ፓ';
  private static char PEE='ፔ';
  private static char PE='ፕ';
  private static char PO='ፖ';
   
  public int stem(char s[], int len) {
    if (s[len-1] == NAA) {
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
              ((s[last-3]==CE &&s[last-2]==HU && s[last-1]==A && s[last]==TE) || 
              (s[last-3]==WAA &&s[last-2]==CE && s[last-1]==XWAA && s[last]==LE) ||
              (s[last-3]==WAA &&s[last-2]==CA && s[last-1]==WAA && s[last]==LE) ||
              (s[last-3]==CA &&s[last-2]==WAA && s[last-1]==LA && s[last]==CE) ||
              (s[last-3]==A && s[last-2]==TAA &&s[last-1]==LA && s[last]==HU) ||
              (s[last-3]==A &&s[last-2]==TAA && s[last-1]==LA && s[last]==SHE) ||
              (s[last-3]==HAA &&s[last-2]==CA && s[last-1]==WAA && s[last]==LE) ||
              (s[last-3]==CA &&s[last-2]==WAA && s[last-1]==LA && s[last]==HE) ||
              (s[last-3]==SHAA &&s[last-2]==A &&s[last-1]==CA && s[last]==WE) || 
              (s[last-3]==SHAA && s[last-2]==CA &&s[last-1]==WAA && s[last]==LE))) { 
      last -= 4;
      len = deleteN(s, last, len, 4);
    }
   
    else if (last > 3 && // 3 SUFFIXES
              ((s[last-2]==SHE &&s[last-1]==NYAA && s[last]==LE) ||
              (s[last-2]==SHE &&s[last-1]==WAA && s[last]==LE) ||
              (s[last-2]==SHAA &&s[last-1]==TAA && s[last]==LE) ||
              (s[last-2]==SHE &&s[last-1]==NAA && s[last]==LE) ||
              (s[last-2]==SHAA && s[last-1]==CA && s[last]==WE) ||
              (s[last-2]==NYAA &&s[last-1]==LA && s[last]==SHE) ||
              (s[last-2]==WAA &&s[last-1]==LA && s[last]==SHE) ||
              (s[last-2]==NAA &&s[last-1]==LA && s[last]==SHE) ||
              (s[last-2]==XWAA &&s[last-1]==CHA && s[last]==WE) ||
              (s[last-2]==KXA &&s[last-1]==NYAA && s[last]==LE) ||
              (s[last-2]==KXA &&s[last-1]==WAA && s[last]==LE) ||
              (s[last-2]==HAA &&s[last-1]==TA && s[last]==LE) ||
              (s[last-2]==KXA &&s[last-1]==NAA && s[last]==LE) ||
              (s[last-2]==NYAA &&s[last-1]==LA && s[last]==HE) ||
              (s[last-2]==WAA &&s[last-1]==LA && s[last]==HE) ||
              (s[last-2]==TAA &&s[last-1]==LA && s[last]==HE) ||
              (s[last-2]==NAA &&s[last-1]==LA && s[last]==HE) ||
              (s[last-2]==XWAA && s[last-1]==CE && s[last]==HU) ||
              (s[last-2]==XWAA &&s[last-1]==CA && s[last]==WE) ||
              (s[last-2]==HAA &&s[last-1]==LA && s[last]==HU) ||
              (s[last-2]==SHAA &&s[last-1]==LA && s[last]==HU) ||
              (s[last-2]==WAA &&s[last-1]==LA && s[last]==HU) ||
              (s[last-2]==CAA &&s[last-1]==CE && s[last]==HU) ||
              (s[last-2]==CAA &&s[last-1]==CE && s[last]==WE) ||
              (s[last-2]==NAA &&s[last-1]==CE && s[last]==HU) ||
              (s[last-2]==NAA &&s[last-1]==CA && s[last]==WE) ||
              (s[last-2]==CE &&s[last-1]==HU && s[last]==NYE) ||
              (s[last-2]==CE &&s[last-1]==HU && s[last]==TE) ||
              (s[last-2]==CE &&s[last-1]==HU && s[last]==NE) ||
              (s[last-2]==NYA &&s[last-1]==LA && s[last]==CE) ||
              (s[last-2]==HAA &&s[last-1]==LA && s[last]==CE)  ||
              (s[last-2]==SHAA &&s[last-1]==LA && s[last]==CE) ||
              (s[last-2]==WAA &&s[last-1]==LA && s[last]==CE) ||
              (s[last-2]==TAA &&s[last-1]==LA && s[last]==CE) ||
              (s[last-2]==NAA &&s[last-1]==LA && s[last]==CE) ||
              (s[last-2]==NE &&s[last-1]==HAA && s[last]==LE) ||
              (s[last-2]==NE &&s[last-1]==SHAA && s[last]==LE) ||
              (s[last-2]==NA &&s[last-1]==WAA && s[last]==LE) ||
              (s[last-2]==NAA &&s[last-1]==TAA && s[last]==LE) ||
              (s[last-2]==WE &&s[last-1]==NYAA && s[last]==LE) ||
              (s[last-2]==WE &&s[last-1]==HAA && s[last]==LE) ||
              (s[last-2]==WE &&s[last-1]==SHAA && s[last]==LE) ||
              (s[last-2]==WE &&s[last-1]==TAA && s[last]==LE) ||
              (s[last-2]==WAA &&s[last-1]==TAA && s[last]==LE) ||
              (s[last-2]==WE &&s[last-1]==NAA && s[last]==LE) ||
              (s[last-2]==WAA &&s[last-1]==CE && s[last]==HU) ||
              (s[last-2]==WAA &&s[last-1]==CE && s[last]==WE) ||
              (s[last-2]==WE && s[last-1]==YAA && s[last]==NE))){
        last -= 3;
        len = deleteN(s, last, len, 3);
    }
    else if ((last > 2) && // 2 SUFFIXES
            ((s[last-1]==SHE && s[last]==NYE) ||
            (s[last-1]==SHE && s[last]==WE) ||
            (s[last-1]==SHAA && s[last]==TE) ||
            (s[last-1]==SHE && s[last]==NE) ||
            (s[last-1]==NYAA && s[last]==LE) ||
            (s[last-1]==HAA && s[last]==LE) ||
            (s[last-1]==SHAA && s[last]==LE) ||
            (s[last-1]==TAA && s[last]==LE) ||
            (s[last-1]==NAA && s[last]==LE) ||
            (s[last-1]==CE && s[last]==HU) ||
            (s[last-1]==CA && s[last]==HU) ||
            (s[last-1]==KXA && s[last]==NYE) ||
            (s[last-1]==KXA && s[last]==WE) ||
            (s[last-1]==HAA && s[last]==TE) ||
            (s[last-1]==KXA && s[last]==NE) ||
            (s[last-1]==HU && s[last]==HE) ||
            (s[last-1]==HU && s[last]==SHE) ||
            (s[last-1]==HU && s[last]==TE) ||
            (s[last-1]==XWAA && s[last]==TE) ||
            (s[last-1]==CE && s[last]==NYE) ||
            (s[last-1]==CE && s[last]==HE) ||
            (s[last-1]==CE && s[last]==SHE) ||
            (s[last-1]==CE && s[last]==WE) ||
            (s[last-1]==CAA && s[last]==TE) ||
            (s[last-1]==CE && s[last]==NE) ||
            (s[last-1]==NE && s[last]==HE) ||
            (s[last-1]==NE && s[last]==SHE) ||
            (s[last-1]==NA && s[last]==WE) ||
            (s[last-1]==NAA && s[last]==TE) ||
            (s[last-1]==WE && s[last]==NYE) ||
            (s[last-1]==WE && s[last]==HE) ||
            (s[last-1]==WE && s[last]==SHE) ||
            (s[last-1]==WE && s[last]==TE) ||
            (s[last-1]==WAA && s[last]==TE) ||
            (s[last-1]==WO && s[last]==CE) ||
            (s[last-1]==YAA && s[last]==NE) ||
            (s[last-1]==YAA && s[last]==TE) ||
            (s[last-1]==NA && s[last]==TE))) {
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

    if ((s[l]==E && s[l+1]==NE && s[l+2]==DE) || // 3 PREFIXES
        (s[l]==E && s[l+1]==NE && s[l+2]==DA) ||
        (s[l]==E && s[l+1]==NE && s[l+2]==DI) ||
        (s[l]==YA && s[l+1]==MAA && s[l+2]==YE)){
      len = deleteN(s, l, len, 3);
      l+=3;
    } else if ((s[l]==E && s[l+1]==NE ) || // 2 PREFIXES
            (s[l]==E && s[l+1]==NA ) ||
            (s[l]==E && s[l+1]==YA ) ||
            (s[l]==A && s[l+1]==LE ) ||
            (s[l]==YA && s[l+1]==MI ) ||
            (s[l]==YA && s[l+1]==ME ) ||
            (s[l]==A && s[l+1]==YE )  ||
            (s[l]==A && s[l+1]==SE ) ||
            (s[l]==BA && s[l+1]==MA ) ||
            (s[l]==YA && s[l+1]==TA)) {
      len = deleteN(s, l, len, 2);
      l+=2;
    } 
    /* else if ((s[l] == E) || // 1 PREFIX 
              (s[l]==LE ) ||
              (s[l]==TE ) ||
              (s[l]==YE ) ||
              (s[l]==BE ) ||
              (s[l]==BA ) ||
              (s[l]==BI ) ||
              (s[l]==KA ) ||
              (s[l]==LA ) ||
              (s[l]==YAA ) ||
              (s[l]==YA ) ||
              (s[l]==LE )){
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
