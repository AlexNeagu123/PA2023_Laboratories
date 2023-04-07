package com.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The <tt>Document</tt>> class represents a real electronic document with a title, path location, tags and metadata.
 * <p>
 * The <tt>Document</tt> class is serializable.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {
    private String id;
    private String title;
    private String location;
    private Map<String, String> tags;
    private Map<String, String> cachedMetadata;

    public Document(String id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
        tags = new HashMap<>();
        cachedMetadata = new HashMap<>();
    }

    /**
     * Add a new tag for this <tt>Document</tt>>
     * If the key of the tag already exists, the insertion doesn't take place (silent behaviour)
     *
     * @param tagKey   The key of the tag
     * @param tagValue The value of the tag
     */
    public void addTag(String tagKey, String tagValue) {
        if (tags.containsKey(tagKey)) {
            return;
        }
        tags.put(tagKey, tagValue);
    }

    /**
     * Add a new metadata tag for this <tt>Document</tt>
     * If the key of the metadata tag already exists, the insertion doesn't take place (silent behaviour)
     *
     * @param metadataKey   The key of the metadata tag
     * @param metadataValue The value of the metadata tag
     */
    public void addMetadata(String metadataKey, String metadataValue) {
        if (cachedMetadata.containsKey(metadataKey)) {
            return;
        }
        cachedMetadata.put(metadataKey, metadataValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();

        stringRepresentation.append("Document's Name: ").append(title).append('\n');
        stringRepresentation.append("Document's Id: ").append(id).append('\n');
        stringRepresentation.append("Document's Path Location: ").append(location).append('\n');
        stringRepresentation.append("Document's Tags: {\n");
        for (String tag : tags.keySet()) {
            stringRepresentation.append("   ").append(tag).append(" : ").append(tags.get(tag)).append("\n");
        }
        stringRepresentation.append("}\n");
        return stringRepresentation.toString();
    }
}
