import { useQuery } from 'react-query';

import Grid from '@mui/material/Grid';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';

import { getProfile } from 'services/users.service';
import { Layout } from 'components/layout.component';

export function MyCourses() {
  const profileQuery = useQuery('profile', getProfile);

  return (
    <Layout title="My Courses">
      <Grid container spacing={2}>
        {profileQuery.data?.courses?.map((course) => (
          <Grid key={course.id} item xs={12} md={6} lg={4}>
            <Card>
              <CardHeader
                avatar={<Avatar>{course.title[0]}</Avatar>}
                title={
                  <Link href={course.url} target="_blank">
                    {course.title}
                  </Link>
                }
              />
              <CardContent>
                <Typography variant="body2" color="text.secondary">
                  {course.description}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Layout>
  );
}
