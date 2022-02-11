package com.youtube.list;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TddArrayListTest
{
    @Test
    void implementsList()
    {
        List<String> list = new TddArrayList<>();
    }

    @Test
    void emptyListHasSizeZero()
    {
        List<String> list = new TddArrayList<>();

        assertThat(list).hasSize(0);
    }

    @Test
    void listWithOneElementHasSizeOne()
    {
        List<String> list = new TddArrayList<>();

        list.add(random(10));

        assertThat(list).hasSize(1);
    }

    @Test
    void canGetElementFromList()
    {
        List<Integer> list = new TddArrayList<>();

        list.add(6);
        list.add(7);

        assertThat(list.get(1)).isEqualTo(7);
    }

    @Test
    void canAddElementAtBeginningOfList()
    {
        List<Integer> list = new TddArrayList<>();

        list.add(6);
        list.add(0, 7);

        assertThat(list).containsExactly(7, 6);
    }

    @Test
    void canRemoveElementsFromList()
    {
        List<String> list = new TddArrayList<>();

        String element = random(10);

        list.add(element);
        list.add(random(10));

        list.remove(1);

        assertThat(list).containsExactly(element);
    }

    @Test
    void canRemoveElementsFromBeginningOfList()
    {
        List<String> list = new TddArrayList<>();

        String element = random(10);

        list.add(random(10));
        list.add(element);

        list.remove(0);

        assertThat(list).containsExactly(element);
    }

    @Test
    void removeReturnsTheRemovedElement()
    {
        List<String> list = new TddArrayList<>();

        String element = random(10);

        list.add(random(10));
        list.add(element);

        assertThat(list.remove(1)).isSameAs(element);
    }

    @Test
    void canRemoveFirstElementInArrayListAtFullCapacity()
    {
        TddArrayList<String> list = new TddArrayList<>();

        range(0, list.currentCapacity()).forEach(__ -> list.add(random(10)));

        assertThat(list).hasSize(list.currentCapacity());

        list.remove(0);

        assertThat(list).hasSize(list.currentCapacity() - 1);
    }

    @Test
    void callingRemoveWithAnOutOfBoundsIndexThrowsException()
    {
        List<String> list = new TddArrayList<>();

        list.add(random(10));

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void callingAddWithAnOutOfBoundsIndexThrowsException()
    {
        List<String> list = new TddArrayList<>();

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, random(10)));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, random(10)));
    }

    @Test
    void canCallSetToReplaceElementInList()
    {
        List<Integer> list = new TddArrayList<>();

        list.add(6);
        list.add(7);
        list.add(8);

        list.set(1, 10);

        assertThat(list).containsExactly(6, 10, 8);
    }

    @Test
    void setReturnsTheUnsetValue()
    {
        List<Integer> list = new TddArrayList<>();

        list.add(6);
        list.add(7);
        list.add(8);

        assertThat(list.set(1, 10)).isEqualTo(7);
    }

    @Test
    void callingGetWithAnOutOfBoundsIndexThrowsException()
    {
        List<String> list = new TddArrayList<>();

        list.add(random(10));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void callingSetWithAnOutOfBoundsIndexThrowsException()
    {
        List<String> list = new TddArrayList<>();

        list.add(random(10));

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, random(10)));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, random(10)));
    }

    @Test
    void canConstructTddArrayListByPassingInExistingList()
    {
        List<String> originalList = List.of(random(10), random(10), random(10));

        List<String> tddList = new TddArrayList<>(originalList);

        assertThat(tddList).containsExactlyElementsOf(originalList);
    }

    @Test
    void canConstructTddArrayListByPassingInExistingSet()
    {
        Set<String> originalSet = Set.of(random(10), random(10), random(10));

        List<String> tddList = new TddArrayList<>(originalSet);

        assertThat(tddList).containsExactlyInAnyOrderElementsOf(originalSet);
    }

    @Test
    void listGrowsInCapacityWhenAddingMoreElementsThanTheCurrentCapacity()
    {
        TddArrayList<String> list = new TddArrayList<>();

        int startingCapacity = list.currentCapacity();

        range(0, startingCapacity).forEach(__ -> list.add(random(10)));

        list.add(list.size() / 2, random(10));

        assertThat(list).hasSize(startingCapacity + 1);
        assertThat(list.currentCapacity()).isGreaterThan(startingCapacity);
    }

    @Test
    void listCanHandleManyAddsAndRemoves()
    {
        TddArrayList<String> list = new TddArrayList<>();

        int totalAddedElements = list.currentCapacity() * 100;
        int totalRemovedElements = list.currentCapacity() * 10;

        range(0, totalAddedElements).forEach(__ -> list.add(nextInt(0, list.size()), random(10)));
        range(0, totalRemovedElements).forEach(__ -> list.remove(nextInt(0, list.size())));

        assertThat(list).hasSize(totalAddedElements - totalRemovedElements);
        assertThat(list.currentCapacity()).isGreaterThanOrEqualTo(totalAddedElements);
    }
}
