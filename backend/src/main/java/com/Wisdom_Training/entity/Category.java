package com.Wisdom_Training.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "wisdom_training")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_category")
    private int idCategory;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> productList;

//    Dùng @Basic khi cần thay đổi cấu hình mặc định
//    Có 2 trường: FetchType và optional
//    FetchType: - EAGER(default): khi load entity, field này cũng được load ngay lập tức.
//               - LAZY: Trì hoãn load, chỉ khi bạn gọi getter thì mới query từ DB.
//    optional: - true: cho phép null.
//              - false: không cho phép null.
}
