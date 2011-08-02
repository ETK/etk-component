package org.etk.core.rest.impl.uri;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;

import org.etk.core.rest.impl.MultivaluedMapImpl;

public final class PathSegmentImpl implements PathSegment {

  /**
   * Path.
   */
  private String                         path;

  /**
   * Matrix parameters.
   */
  private MultivaluedMap<String, String> matrixParameters;

  /**
   * @param path Path
   * @param matrixParameters Matrix parameters
   */
  PathSegmentImpl(String path, MultivaluedMap<String, String> matrixParameters) {
    this.path = path;
    this.matrixParameters = matrixParameters;
  }

  /**
   * Create instance of PathSegment from given string.
   * 
   * @param pathSegment string which represents PathSegment
   * @param decode true if character must be decoded false otherwise
   * @return instance of PathSegment
   */
  public static PathSegment fromString(String pathSegment, boolean decode) {
    String path = "";
    MultivaluedMap<String, String> m = new MultivaluedMapImpl();
    if (pathSegment == null || pathSegment.length() == 0)
      return new PathSegmentImpl(path, m);

    int n = 0;
    // first ';' the start point for matrix parameter
    int p = pathSegment.indexOf(';', n);

    if (p > 0)
      path = pathSegment.substring(0, p);
    else
      path = pathSegment;
    if (decode)
      path = UriComponent.decode(path, UriComponent.PATH_SEGMENT);
    

    if (p < 0) // no matrix parameters
      return new PathSegmentImpl(path, m); 

    p++; // next character after ';'
    int length = pathSegment.length();
    while (p < length) {
      n = pathSegment.indexOf(';', p); // find next ';'
      String pair; // should look like 'a=b', but value can absent
      if (n < 0) { // last pair in the string
        n = pathSegment.length();
        pair = pathSegment.substring(p);
      } else
        pair = pathSegment.substring(p, n);

      String name; 
      String value = ""; // default value
      int eq = pair.indexOf('=');
      if (eq == -1) // no value, default is ""
        name = pair;
      else {
        name = pair.substring(0, eq);
        value = pair.substring(eq + 1);
      }

      m.add(decode ? UriComponent.decode(name, UriComponent.PATH_SEGMENT) : name,
            decode ? UriComponent.decode(value, UriComponent.PATH_SEGMENT) : value);
      
      p = n + 1;
    }

    return new PathSegmentImpl(path, m);
  }

  /**
   * {@inheritDoc}
   */
  public MultivaluedMap<String, String> getMatrixParameters() {
    return matrixParameters;
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {
    return path;
  }

}

