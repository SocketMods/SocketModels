package dev.socketmods.socketmodels.api.rewards;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

//TODO: Unit Tests
public class Person {

    @Nullable
    public final String name;
    public final String uuid;
    public final Set<String> permissions;

    private final transient boolean isContributor;

    public Person(@Nullable String name, String uuid, Set<String> permissions) {
        this.name = name;
        this.uuid = uuid;
        this.permissions = Collections.unmodifiableSet(permissions.stream().filter(Objects::nonNull).map(String::toLowerCase).collect(Collectors.toSet()));

        this.isContributor = permissions.contains(KnownPermissions.CONTRIBUTOR);
    }

    public boolean isContributor() {
        return isContributor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && uuid.equals(person.uuid) && permissions.equals(person.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, permissions);
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("uuid", uuid)
            .add("permissions", permissions)
            .add("isContributor", isContributor)
            .toString();
    }

    public static Person merge(Person left, Person right) {
        Set<String> permissions = new HashSet<>(left.permissions);
        permissions.addAll(right.permissions);

        return new Person(left.name, left.uuid, permissions);
    }

    /**
     * Fuck you gson
     */
    public static @Nullable Person recreate(@Nullable Person input) {
        if (input == null) return null;

        return new Person(input.name, input.uuid, input.permissions);
    }

}
