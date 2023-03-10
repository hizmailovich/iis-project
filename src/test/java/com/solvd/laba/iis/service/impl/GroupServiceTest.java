package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.GroupRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void verifyRetrieveAllTest() {
        List<Group> expectedGroups = createGroups();
        when(groupRepository.findAll()).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveAll();
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    public void verifyRetrieveByIdSuccessTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.findById(expectedGroup.getId())).thenReturn(Optional.of(expectedGroup));
        Group group = groupService.retrieveById(expectedGroup.getId());
        assertEquals(expectedGroup, group, "Objects are not equal");
        verify(groupRepository, times(1)).findById(expectedGroup.getId());
    }

    @Test
    public void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        Long groupId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> groupService.retrieveById(groupId));
        verify(groupRepository, times(1)).findById(groupId);
    }

    @Test
    public void verifyRetrieveByTeacherAndSubjectTest() {
        List<Group> expectedGroups = createGroups();
        Long teacherId = 1L, subjectId = 1L;
        when(groupRepository.findByTeacherAndSubject(teacherId, subjectId)).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByTeacherAndSubject(teacherId, subjectId);
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByTeacherAndSubject(teacherId, subjectId);
    }

    @Test
    public void verifyRetrieveByTeacherCriteriaTest() {
        List<Group> expectedGroups = createGroups();
        Long teacherId = 1L;
        when(groupRepository.findByCriteria(teacherId, new GroupSearchCriteria())).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByCriteria(teacherId, new GroupSearchCriteria());
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByCriteria(teacherId, new GroupSearchCriteria());
    }

    @Test
    public void retrieveByTeacherAndSubjectCriteriaTest() {
        List<Group> expectedGroups = createGroups();
        Long teacherId = 1L, subjectId = 1L;
        when(groupRepository.findByCriteria(teacherId, new GroupSearchCriteria(subjectId))).thenReturn(expectedGroups);
        List<Group> groups = groupService.retrieveByCriteria(teacherId, new GroupSearchCriteria(subjectId));
        assertEquals(expectedGroups, groups, "Objects are not equal");
        verify(groupRepository, times(1)).findByCriteria(teacherId, new GroupSearchCriteria(subjectId));
    }

    @Test
    public void verifyCreateSuccessTest() {
        Long groupId = 1L;
        Group group = createGroup();
        group.setId(null);
        when(groupRepository.isExist(anyInt())).thenReturn(false);
        doAnswer(invocation -> {
            Group receivedGroup = invocation.getArgument(0);
            receivedGroup.setId(groupId);
            return null;
        }).when(groupRepository).create(group);
        group = groupService.create(group);
        assertEquals(groupId, group.getId(), "Objects are not equal");
        verify(groupRepository, times(1)).isExist(anyInt());
        verify(groupRepository, times(1)).create(any(Group.class));
    }

    @Test
    public void verifyCreateThrowsResourceAlreadyExistExceptionTest() {
        Group expectedGroup = createGroup();
        when(groupRepository.isExist(expectedGroup.getNumber())).thenReturn(true);
        assertThrows(ResourceAlreadyExistException.class, () -> groupService.create(expectedGroup));
        verify(groupRepository, times(1)).isExist(expectedGroup.getNumber());
        verify(groupRepository, times(0)).create(expectedGroup);
    }

    @Test
    public void verifyUpdateTest() {
        Group oldGroup = createGroup();
        Group newGroup = createGroup();
        newGroup.setNumber(950501);
        when(groupRepository.findById(newGroup.getId())).thenReturn(Optional.of(oldGroup));
        Group group = groupService.update(newGroup);
        assertEquals(newGroup, group, "Objects are not equal");
        verify(groupRepository, times(1)).findById(newGroup.getId());
        verify(groupRepository, times(1)).update(newGroup);
    }

    @Test
    public void verifyDeleteTest() {
        Long groupId = 1L;
        groupService.delete(groupId);
        verify(groupRepository, times(1)).delete(groupId);
    }

    private Group createGroup() {
        return new Group(1L, 951005);
    }

    private List<Group> createGroups() {
        return Lists.newArrayList(
                new Group(1L, 951005),
                new Group(2L, 952003),
                new Group(3L, 953004));
    }

}
