package com.github.dmitrKuznetsov.stb.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="tg_user")
public class TelegramUser {

    @Id
    @Column(name="chat_id")
    private String chatId;

    @Column(name="active")
    private Boolean active;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<GroupSub> groupSubs;
}
