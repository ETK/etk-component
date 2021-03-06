package org.etk.orm.plugins.bean.type;

import java.util.HashMap;
import java.util.Map;

import org.etk.orm.plugins.bean.mapping.jcr.PropertyMetaType;


/**
 * This code is synchronized. Normally it should not have performance impact on runtime, i.e
 * this should not be used at runtime and the result should be cached somewhere in the runtime layer.
 *
 */
class PropertyTypeEntry {

  /** . */
  private final SimpleTypeMappingImpl<?> defaultValueTypeInfo;

  /** . */
  private final Map<PropertyMetaType<?>, SimpleTypeMappingImpl<?>> metaTypeMapping;

  PropertyTypeEntry(PropertyTypeEntry that) {
    this.defaultValueTypeInfo = that.defaultValueTypeInfo;
    this.metaTypeMapping = new HashMap<PropertyMetaType<?>, SimpleTypeMappingImpl<?>>(that.metaTypeMapping);
  }

  PropertyTypeEntry(SimpleTypeMappingImpl<?> defaultValueTypeInfo) {
    Map<PropertyMetaType<?>, SimpleTypeMappingImpl<?>> metaTypeMapping = new HashMap<PropertyMetaType<?>, SimpleTypeMappingImpl<?>>();
    metaTypeMapping.put(defaultValueTypeInfo.getPropertyMetaType(), defaultValueTypeInfo);

    //
    this.defaultValueTypeInfo = defaultValueTypeInfo;
    this.metaTypeMapping = metaTypeMapping;
  }

  public SimpleTypeMappingImpl<?> getDefault() {
    return defaultValueTypeInfo;
  }

  public synchronized <I> SimpleTypeMappingImpl<I> add(SimpleTypeMappingImpl<I> valueType) {
    if (!valueType.external.equals(defaultValueTypeInfo.external)) {
      throw new IllegalArgumentException("Was expecting those types to be equals " + valueType.external + " " + defaultValueTypeInfo.external);
    }
    metaTypeMapping.put(valueType.getPropertyMetaType(), valueType);
    return valueType;
  }

  public synchronized <I> SimpleTypeMappingImpl<I> get(PropertyMetaType<I> propertyMT) {
    return (SimpleTypeMappingImpl<I>)metaTypeMapping.get(propertyMT);
  }

  public synchronized SimpleTypeMappingImpl<?> resolve(PropertyMetaType<?> propertyMT) {
    SimpleTypeMappingImpl<?> valueTypeInfo = metaTypeMapping.get(propertyMT);
    if (valueTypeInfo == null) {
      valueTypeInfo = defaultValueTypeInfo;
    }
    return valueTypeInfo;
  }
}
