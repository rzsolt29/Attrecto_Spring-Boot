import React from 'react';
import { useQuery } from 'react-query';

import Box from '@mui/material/Box';
import { DataGrid, GridColDef, GridActionsCellItem, GridActionsColDef } from '@mui/x-data-grid';

import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';

import { Layout } from 'components/layout.component';
import { ConfirmDialog } from 'components/confirm-dialog.component';
import { getCourses, deleteCourse } from 'services/courses.service';
import { UserModel } from 'models/user.model';
import { CourseModel } from 'models/course.model';
import { useEditorState } from 'utils/use-editor-state';

import { CourseEditor } from './course-editor.component';

export function Courses() {
  const query = useQuery('courses', getCourses);

  const {
    isOpen: editorDialogOpen,
    handleOpen: handleEditorDialogOpen,
    handleClose: handleEditorDialogClose,
    data: editorValue,
  } = useEditorState<CourseModel>();
  const {
    isOpen: deleteDialogOpen,
    handleOpen: handleDeleteDialogOpen,
    handleClose: handleDeleteDialogClose,
    data: deleteValue,
  } = useEditorState<CourseModel>();

  const onDelete = async () => {
    if (deleteValue?.id) {
      await deleteCourse(deleteValue.id);
      await query.refetch();
    }
  };

  const columns: (GridColDef | GridActionsColDef)[] = [
    { field: 'title', headerName: 'Title', flex: 5 },
    {
      field: 'author',
      renderCell: (params) => (params.value as UserModel)?.name,
      headerName: 'Author',
      flex: 3,
    },
    {
      field: 'actions',
      type: 'actions',
      flex: 1,
      renderHeader: () => {
        return <GridActionsCellItem icon={<AddIcon />} label="Add" onClick={() => handleEditorDialogOpen()} />;
      },
      getActions: (params) => [
        <GridActionsCellItem
          onClick={() => {
            handleEditorDialogOpen(params?.row as CourseModel);
          }}
          icon={<EditIcon />}
          label="Edit"
          showInMenu
        />,
        <GridActionsCellItem
          onClick={() => {
            handleDeleteDialogOpen(params?.row as CourseModel);
          }}
          icon={<DeleteIcon />}
          label="Delete"
          showInMenu
        />,
      ],
    },
  ];

  return (
    <Layout title="Courses">
      <CourseEditor
        open={editorDialogOpen}
        onClose={handleEditorDialogClose}
        onSubmit={query.refetch}
        data={editorValue}
      />
      <ConfirmDialog
        title="Delete Course"
        open={deleteDialogOpen}
        onClose={handleDeleteDialogClose}
        onSubmit={onDelete}
      >
        {deleteValue ? `Are you sure you want to delete ${deleteValue.title}?` : 'Are you sure you want to delete?'}
      </ConfirmDialog>
      <Box flex={1}>
        <DataGrid
          rows={query.data || []}
          columns={columns}
          disableSelectionOnClick
          hideFooterPagination
          loading={query.isLoading}
        />
      </Box>
    </Layout>
  );
}
