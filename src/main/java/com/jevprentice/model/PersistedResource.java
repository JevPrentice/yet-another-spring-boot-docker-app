package com.jevprentice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "\"persisted_resource\"")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class PersistedResource implements Serializable {

    private static final long serialVersionUID = SerializableVersion.VERSION;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Size(min = 1)
    @Column(nullable = false)
    private @NonNull String fileName;

    @Size(min = 1)
    @Column(nullable = false)
    private @NonNull String contentType;

    @Column(nullable = false)
    private byte[] fileBytes;

    public PersistedResource(final String fileName, final String contentType, final byte[] fileBytes) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileBytes = fileBytes.clone();
    }

    public byte[] getFileBytes() {
        return fileBytes.clone();
    }

    public void setFileBytes(final byte[] fileBytes) {
        this.fileBytes = fileBytes.clone();
    }
}
