package dev.socketmods.socketmodels.utils.functional;

public interface ThrowableConsumer<T, E extends Throwable> {

    void accept(T t) throws E;

}
