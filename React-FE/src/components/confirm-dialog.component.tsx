import React, { FC, ReactNode, useState } from 'react';

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';

interface ConfirmDialogProps {
  open: boolean;
  title: ReactNode;
  onSubmit: () => any;
  onClose: () => any;
  confirmTitle?: ReactNode;
  cancelTitle?: ReactNode;
  loading?: boolean;
}

export const ConfirmDialog: FC<ConfirmDialogProps> = ({
  open,
  title,
  children,
  onSubmit,
  loading: loadingProp,
  confirmTitle,
  cancelTitle,
  onClose,
}) => {
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (onSubmit) {
      setLoading(true);

      try {
        await onSubmit();

        if (onClose) {
          onClose();
        }
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>{children}</DialogContent>
      <DialogActions>
        <Box mt={2} display="flex">
          <Box mr={2}>
            <Button variant="outlined" color="secondary" onClick={onClose} disabled={loading || loadingProp}>
              {cancelTitle || 'Cancel'}
            </Button>
          </Box>
          <Button variant="contained" onClick={handleSubmit} disabled={loading || loadingProp} autoFocus>
            {confirmTitle || 'Confirm'}
          </Button>
        </Box>
      </DialogActions>
    </Dialog>
  );
};
