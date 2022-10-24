import React, { FC, useEffect, useState } from 'react';

import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';

import { EventEmitter } from 'utils/event-emitter';

const notificationEventEmitter = new EventEmitter();

enum NotificationEvents {
  SHOW_NOTIFICATION = 'SHOW_NOTIFICATION',
}

export type NotificationContent = string;

export enum NotificationType {
  SUCCESS = 'success',
  INFO = 'info',
  WARNING = 'warning',
  ERROR = 'error',
}

export interface NotificationModel {
  content: NotificationContent;
  type: NotificationType;
}

export const showNotification = (data: NotificationModel) => {
  notificationEventEmitter.dispatch(NotificationEvents.SHOW_NOTIFICATION, data);
};

export const NotificationProvider: FC = () => {
  const [notification, setNotification] = useState<NotificationModel | null>(null);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const unsubscribe = notificationEventEmitter.subscribe(NotificationEvents.SHOW_NOTIFICATION, (data) => {
      setNotification(data);
      setOpen(true);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  const onClose = () => {
    setOpen(false);
  };

  const onExited = () => {
    setOpen(false);
    setNotification(null);
  };

  return (
    <Snackbar
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={open}
      onEnded={onExited}
      autoHideDuration={500000}
      onClose={onClose}
    >
      <Alert onClose={onClose} severity={notification?.type} sx={{ width: '100%' }}>
        {notification?.content}
      </Alert>
    </Snackbar>
  );
};
