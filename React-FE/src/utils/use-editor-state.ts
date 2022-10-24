import { useState } from 'react';

export interface UseEditorStateOptions<T> {
  initialValue?: T | null;
  initialOpen?: boolean;
}

export function useEditorState<T>(options?: UseEditorStateOptions<T>) {
  const [isOpen, setIsOpen] = useState(options?.initialOpen || false);
  const [data, setData] = useState<T | null>(options?.initialValue || null);

  const handleOpen = (value?: T) => {
    setIsOpen(true);
    setData(value || null);
  };

  const handleClose = () => {
    setIsOpen(false);
    setData(null);
  };

  return {
    isOpen,
    data,
    handleOpen,
    handleClose,
  };
}
