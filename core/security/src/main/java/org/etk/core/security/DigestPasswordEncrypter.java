/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.etk.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform
 * thanhvucong.78@google.com Aug 13, 2011
 */
public class DigestPasswordEncrypter implements PasswordEncrypter {
  final private int HASH_HEX_LENGTH = 32;

  final private Map<String, String> context;

  final private String username;

  public DigestPasswordEncrypter(String username, Map<String, String> context) {
    this.context = context;
    this.username = username;
  }

  /**
   * Number of HEX digits used for A1, A2 strings and password encoding. More
   * information is settled in<a
   * href=http://www.apps.ietf.org/rfc/rfc2617.html#sec-3.2.2>RFC-2617</a>.
   */

  private String convertToHex(byte[] bin) {
    StringBuffer tmpStr = new StringBuffer(HASH_HEX_LENGTH);
    int digit;

    for (int i = 0; i < HASH_HEX_LENGTH / 2; i++) {
      // get integer presentation of left 4 bits of byte
      digit = (bin[i] >> 4) & 0xf;
      // append HEX digit
      tmpStr.append(Integer.toHexString(digit));
      // get integer presentation of right 4 bits of byte
      digit = bin[i] & 0xf;
      tmpStr.append(Integer.toHexString(digit));

    }
    ;
    return tmpStr.toString();
  }

  public byte[] encrypt(byte[] plainPassword) {
    String nc = context.get("nc");
    String a2 = context.get("md5a2");
    String uri = context.get("uri");
    String qop = context.get("qop");
    String nonce = context.get("nonce");
    String realm = context.get("realmName");
    String cnonce = context.get("cnonce");
    String entity = context.get("entity");
    String method = context.get("method");
    if (realm == null) {
      // in case we have a jboss server, it uses 'realm' name
      realm = context.get("realm");
    }
    if (a2 == null) {
      // in case we have a jboss server, it uses 'a2hash' name
      a2 = context.get("a2hash");
    }

    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      // TODO add exologger
    }
    // calculate MD5 hash of A1 string
    String a1 = username + ":" + realm + ":" + new String(plainPassword);
    md.update(a1.getBytes());
    // encode A1 in HEX digits
    a1 = convertToHex(md.digest());

    // if encoded A2 MD5 hash is not supplied by server
    // we need to calculate it manually
    if (a2 == null) {
      if (qop.equals("auth")) {
        md.update((method + ":" + uri).getBytes());
        a2 = convertToHex(md.digest());
      } else if (qop.equals("auth-int")) {
        md.update((method + ":" + uri + ":" + convertToHex(entity.getBytes())).getBytes());
        a2 = convertToHex(md.digest());
      }
    }

    // create a digest using provided data
    String digest = a1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + a2;
    md.update(digest.getBytes());
    // return encoded hash using HEX digits digest
    return convertToHex(md.digest()).getBytes();

  }
}
