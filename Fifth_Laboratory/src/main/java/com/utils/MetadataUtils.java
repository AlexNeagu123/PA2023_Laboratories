package com.utils;

import java.util.Map;

public class MetadataUtils {
    /**
     * @param firstSetOfMetadata  The first set of tags (key-value pairs)
     * @param secondSetOfMetadata The second set of tags (key-value pairs)
     * @return <tt>true</tt> if there is a common key-value pair between the sets, <tt>false</tt> otherwise.
     */
    public static boolean existsCommonTag(Map<String, String> firstSetOfMetadata, Map<String, String> secondSetOfMetadata) {
        for (String key : firstSetOfMetadata.keySet()) {
            if (!secondSetOfMetadata.containsKey(key)) {
                continue;
            }
            Object firstValue = firstSetOfMetadata.get(key);
            Object secondValue = secondSetOfMetadata.get(key);
            if (firstValue.equals(secondValue)) {
                return true;
            }
        }
        return false;
    }
}
