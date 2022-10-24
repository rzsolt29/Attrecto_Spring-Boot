import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage, FieldProps } from 'formik';
import { useQuery } from 'react-query';
import * as Yup from 'yup';

import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import ListItem from '@mui/material/ListItem';
import Autocomplete, { AutocompleteRenderInputParams } from '@mui/material/Autocomplete/Autocomplete';
import Button from '@mui/material/Button';

import { createCourse, editCourse } from 'services/courses.service';
import { getUsers } from 'services/users.service';
import { CourseModel } from 'models/course.model';
import { CourseResponseModel } from 'models/courseresponse.model';
import { UserModel } from 'models/user.model';

export interface CourseEditorProps {
  open: boolean;
  onClose: () => void;
  onSubmit: () => Promise<any>;
  data: CourseModel | null;
}

export function CourseEditor({ open, onClose, onSubmit: afterSubmit, data }: CourseEditorProps) {
  const [isLoading, setIsLoading] = useState(false);
  const query = useQuery('users', getUsers);

  const onSubmit = async (values: CourseModel) => {
    try {
      setIsLoading(true);
 
	  var studentIds : number[] = [];
	  
	  let studentsCount = 0;
	  if (typeof values.students !== 'undefined'){
		studentsCount = values.students.length;
	  }
	  
	  for(let i = 0; i < studentsCount; i++) {
	    var students : any = values?.students;
		studentIds[i] = students[i].id;
	  }
  	  
	  const courseResponseModel : CourseResponseModel = {
		  title : values.title,
		  description : values.description,
		  url : values.url,
		  authorId : values.author?.id,
		  studentIds : studentIds
	  }
	  
      if (data && data.id) {
        await editCourse(data.id, courseResponseModel);
      } else {
        await createCourse(courseResponseModel);
      }

      await afterSubmit();
      onClose();
      setIsLoading(false);
    } catch (e) {
      setIsLoading(false);
    }
  };

  return (
    <Dialog maxWidth="md" fullWidth onClose={onClose} open={open}>
      <DialogTitle>{data?.id ? 'Edit course' : 'Add course'}</DialogTitle>
      <DialogContent>
        <Formik<CourseModel>
          initialValues={{ title: '', description: '', url: '', author: null, students: [], ...data } as CourseModel}
          validationSchema={Yup.object().shape({
            title: Yup.string().required(),
            description: Yup.string(),
            url: Yup.string().url().required(),
          })}
          validateOnChange={false}
          onSubmit={onSubmit}
        >
          <Form>
            <Stack mt={1} spacing={2}>
              <Box>
                <Field as={TextField} name="title" label="Title" fullWidth />
                <ErrorMessage name="title" />
              </Box>
              <Box>
                <Field as={TextField} name="description" label="Description" fullWidth />
                <ErrorMessage name="description" />
              </Box>
              <Box>
                <Field as={TextField} name="url" label="URL" fullWidth />
                <ErrorMessage name="url" />
              </Box>
              <Box>
                <Field name="author">
                  {(props: FieldProps<UserModel>) => (
                    <Autocomplete
                      value={props.field.value}
                      isOptionEqualToValue={(option, value) => option.id === value.id}
                      renderOption={(props, option: UserModel) => (
                        <ListItem {...props} key={option.id}>
                          {option.name}
                        </ListItem>
                      )}
                      getOptionLabel={(option) => option.name}
                      options={query.data || []}
					  onChange={(e, value) => {
                        props.form.setFieldValue('author', value);
                      }}
                      renderInput={(params: AutocompleteRenderInputParams) => (
                        <TextField {...params} fullWidth label="Author"/>
                      )}
                    />
                  )}
                </Field>
                <ErrorMessage name="author" />
              </Box>
              <Box>
                <Field name="students">
                  {(props: FieldProps<UserModel[]>) => (
                    <Autocomplete
                      value={props.field.value}
                      isOptionEqualToValue={(option, value) => option.id === value.id}
                      renderOption={(props, option: UserModel) => (
                        <ListItem {...props} key={option.id}>
                          {option.name}
                        </ListItem>
                      )}
                      getOptionLabel={(option) => option.name}
                      options={query.data || []}
                      multiple
                      onChange={(e, value) => {
                        props.form.setFieldValue('students', value);
                      }}
                      renderInput={(params: AutocompleteRenderInputParams) => (
                        <TextField {...params} fullWidth label="Students" />
                      )}
                    />
                  )}
                </Field>
                <ErrorMessage name="students" />
              </Box>
              <Button type="submit" variant="contained" disabled={isLoading}>
                Save
              </Button>
            </Stack>
          </Form>
        </Formik>
      </DialogContent>
    </Dialog>
  );
}
