import React from 'react';
import { useQuery } from 'react-query';

import Box from '@mui/material/Box';
import { DataGrid, GridActionsCellItem, GridActionsColDef, GridColDef } from '@mui/x-data-grid';

import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

import { Layout } from 'components/layout.component';
import { ConfirmDialog } from 'components/confirm-dialog.component';
import { deleteUser, getUsers } from 'services/users.service';
import { UserModel } from 'models/user.model';
import { useEditorState } from 'utils/use-editor-state';

import { UserEditor } from './user-editor.component';

export function Users() {
  const query = useQuery('users', getUsers);

  const {
    isOpen: editorDialogOpen,
    handleOpen: handleEditorDialogOpen,
    handleClose: handleEditorDialogClose,
    data: editorValue,
  } = useEditorState<UserModel>();
  const {
    isOpen: deleteDialogOpen,
    handleOpen: handleDeleteDialogOpen,
    handleClose: handleDeleteDialogClose,
    data: deleteValue,
  } = useEditorState<UserModel>();

  const onDelete = async () => {
    if (deleteValue?.id) {
      await deleteUser(deleteValue.id);
      await query.refetch();
    }
  };

  const columns: (GridColDef | GridActionsColDef)[] = [
    { field: 'name', headerName: 'Name', flex: 3 },
    { field: 'email', headerName: 'Email', flex: 4 },
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
            handleEditorDialogOpen(params?.row as UserModel);
          }}
          icon={<EditIcon />}
          label="Edit"
          showInMenu
        />,
        <GridActionsCellItem
          onClick={() => {
            handleDeleteDialogOpen(params?.row as UserModel);
          }}
          icon={<DeleteIcon />}
          label="Delete"
          showInMenu
        />,
      ],
    },
  ];

  return (
    <Layout title="Users">
      <UserEditor
        open={editorDialogOpen}
        onClose={handleEditorDialogClose}
        onSubmit={query.refetch}
        data={editorValue}
      />
      <ConfirmDialog title="Delete User" open={deleteDialogOpen} onClose={handleDeleteDialogClose} onSubmit={onDelete}>
        {deleteValue ? `Are you sure you want to delete ${deleteValue.name}?` : 'Are you sure you want to delete?'}
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
