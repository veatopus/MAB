package com.example.mab.ui.bottom_navigation.models;

import com.example.mab.MediaByIdQuery;

public class CharacterAniList {
    MediaByIdQuery.Node1 node;
    MediaByIdQuery.Edge edge;

    public CharacterAniList(MediaByIdQuery.Node1 node, MediaByIdQuery.Edge edge) {
        this.node = node;
        this.edge = edge;
    }

    public MediaByIdQuery.Node1 getNode() {
        return node;
    }

    public MediaByIdQuery.Edge getEdge() {
        return edge;
    }
}