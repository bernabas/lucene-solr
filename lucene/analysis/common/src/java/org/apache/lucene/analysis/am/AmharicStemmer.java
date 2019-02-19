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
    {'\u1200', '\u1201','\u1202','\u1203','\u1204','\u1205','\u1206'},
    {'\u1208', '\u1209','\u120A','\u120B','\u120C','\u120D','\u120E'},
    {'\u1210', '\u1211','\u1212','\u1213','\u1214','\u1215','\u1216'},
    {'\u1218', '\u1219','\u121A','\u121B','\u121C','\u121D','\u121E'},
    {'\u1220', '\u1221','\u1222','\u1223','\u1224','\u1225','\u1226'},
    {'\u1228', '\u1229','\u122A','\u122B','\u122C','\u122D','\u122E'},
    {'\u1230', '\u1231','\u1232','\u1233','\u1234','\u1235','\u1236'},
    {'\u1238', '\u1239','\u123A','\u123B','\u123C','\u123D','\u123E'},
    {'\u1240', '\u1241','\u1242','\u1243','\u1244','\u1245','\u1246'},
    {'\u1260', '\u1261','\u1262','\u1263','\u1264','\u1265','\u1266'},
    {'\u1268', '\u1269','\u126A','\u126B','\u126C','\u126D','\u126E'},
    {'\u1270', '\u1271','\u1272','\u1273','\u1274','\u1275','\u1276'},
    {'\u1278', '\u1279','\u127A','\u127B','\u127C','\u127D','\u127E'},
    {'\u1280', '\u1281','\u1282','\u1283','\u1284','\u1285','\u1286'},
    {'\u1290', '\u1291','\u1292','\u1293','\u1294','\u1295','\u1296'},
    {'\u1298', '\u1299','\u129A','\u129B','\u129C','\u129D','\u129E'},
    {'\u12A0', '\u12A1','\u12A2','\u12A3','\u12A4','\u12A5','\u12A6'},
    {'\u12A8', '\u12A9','\u12AA','\u12AB','\u12AC','\u12AD','\u12AE'},
    {'\u12B8', '\u12B9','\u12BA','\u12BB','\u12BC','\u12BD','\u12BE'},
    {'\u12C8', '\u12C9','\u12CA','\u12CB','\u12CC','\u12CD','\u12CE'},
    {'\u12D0', '\u12D1','\u12D2','\u12D3','\u12D4','\u12D5','\u12D6'},
    {'\u12D8', '\u12D9','\u12DA','\u12DB','\u12DC','\u12DD','\u12DE'},
    {'\u12E0', '\u12E1','\u12E2','\u12E3','\u12E4','\u12E5','\u12E6'},
    {'\u12E8', '\u12E9','\u12EA','\u12EB','\u12EC','\u12ED','\u12EE'},
    {'\u12F0', '\u12F1','\u12F2','\u12F3','\u12F4','\u12F5','\u12F6'},
    {'\u12F8', '\u12F9','\u12FA','\u12FB','\u12FC','\u12FD','\u12FE'},
    {'\u1300', '\u1301','\u1302','\u1303','\u1304','\u1305','\u1306'},
    {'\u1308', '\u1309','\u130A','\u130B','\u130C','\u130D','\u130E'},
    {'\u1320', '\u1321','\u1322','\u1323','\u1324','\u1325','\u1326'},
    {'\u1328', '\u1329','\u132A','\u132B','\u132C','\u132D','\u132E'},
    {'\u1340', '\u1341','\u1342','\u1343','\u1344','\u1345','\u1346'},
    {'\u1348', '\u1349','\u134A','\u134B','\u134C','\u134D','\u134E'},
    {'\u1350', '\u1351','\u1352','\u1353','\u1354','\u1355','\u1356'}
  };

  private static char HA ='ሀ';
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
   
  public int stem(char buffer[], int len) {
    len = stemPrefix(buffer, len);
    len = stemSuffix(buffer, len);
    return len;
  }

  public int stemPrefix(char s[], int len) {
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
    } else if ((s[l] == E) || // 1 PREFIX 
              (s[l]==LE ) ||
              (s[l]==TE ) ||
              (s[l]==YE ) ||
              (s[l]==BE ) ||
              (s[l]==BA ) ||
              (s[l]==BI ) ||
              (s[l]==MA ) ||
              (s[l]==KA ) ||
              (s[l]==LA ) ||
              (s[l]==YAA ) ||
              (s[l]==YA ) ||
              (s[l]==LE )){
      len = deleteN(s, l, len, 2);        
      l+=1;
    }
    return len;
  }

  public int stemSuffix(char s[], int len) {
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
    } else if (last > 3 && // 3 SUFFIXES
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

    return len;
  }
}
