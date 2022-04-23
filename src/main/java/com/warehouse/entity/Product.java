package com.warehouse.entity;

import com.warehouse.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbsEntity {
    @ManyToOne(optional = false)  // = @NotNull
    Category category;

    @OneToOne
    private Attachment photo;

    @Column(nullable = false)
    private String code;

    @ManyToOne(optional = false)
    private Measurement measurement;
}
