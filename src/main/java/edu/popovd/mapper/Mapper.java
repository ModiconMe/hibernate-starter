package edu.popovd.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
