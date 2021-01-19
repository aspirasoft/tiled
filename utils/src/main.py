import argparse
import os

import cv2
import numpy as np

parser = argparse.ArgumentParser(description='Make tiles from an image.')
parser.add_argument('src', help='The image to make tiles from.')
parser.add_argument('t_w', help='The width of a tile. Must be divisible by image width.', type=int)
parser.add_argument('t_h', help='The height of a tile. Must be divisible by image height.', type=int)
parser.add_argument('--save', help='Path where tile images will be saved. Current directory by default.',
                    default='./')
args = parser.parse_args()

# Open input image
prefix = os.path.splitext(os.path.split(args.src)[-1])[0].strip().lower().replace('_', '').replace('-', '')
image = cv2.imread(args.src, cv2.IMREAD_UNCHANGED)
h, w, _ = image.shape

# Confirm tile dimensions are divisible by image dimensions
assert h % args.t_h == 0
assert w % args.t_w == 0

# Define Android project structure
source_dir = os.path.join(args.save, 'app/src/main/java/')
assets_dir = os.path.join(args.save, 'app/src/main/assets/')
images_dir = os.path.join(args.save, 'app/src/main/res/drawable/')
layout_dir = os.path.join(args.save, 'app/src/main/res/layout/')

# Create project directories if they don't exist
for folder in [source_dir, assets_dir, images_dir, layout_dir]:
    if not os.path.exists(folder):
        os.makedirs(folder)

# Calculate number of tiles in image, and create an array to save them
count_h = int(h / args.t_h)
count_w = int(w / args.t_w)
count = (count_h, count_w)

idx = 0
valid_ids = [chr(i) for i in range(ord('a'), ord('z') + 1)]
tile_ids = np.zeros(shape=count, dtype=object)
tiles = dict()

# Slide a tile-sized window on source image
for r in range(0, int(h - args.t_h) + 1, int(args.t_h)):
    for c in range(0, int(w - args.t_w) + 1, int(args.t_w)):
        ih = int(r / args.t_h)
        iw = int(c / args.t_w)

        # Crop out the tile
        tile = image[r:r + args.t_h, c:c + args.t_w, :]

        # If this is a transparent tile, ignore it
        if (tile == np.zeros(tile.shape)).all():
            continue

        # If a visually similar tile was cropped before, assign this
        # tile the same id as that one.
        exists = False
        for key, v in tiles.items():
            if (v == tile).all():
                tile_ids[ih][iw] = key
                exists = True
                break

        # If this is a new tile, assign it a unique id
        if not exists:
            # assign it a unique key
            key = ''
            for j in range(int(idx / len(valid_ids)) + 1):
                key += valid_ids[int(idx % len(valid_ids))]

            # save cropped tile image
            outfile = os.path.join(images_dir, f't_{prefix}_{key}.png')
            cv2.imwrite(outfile, tile)

            tiles[key] = tile
            tile_ids[ih][iw] = key
            idx += 1

# Generate tilemap data and lookup table
map_data = ''
map_lookup_table = ''
processed = []
for r in range(tile_ids.shape[0]):
    for c in range(tile_ids.shape[1]):
        key = tile_ids[r][c]
        map_data += f'{key}|'
        if key in processed:
            continue

        outfile = f't_{prefix}_{key}'
        map_lookup_table += f'tilemap.addImage("{key}", R.drawable.{outfile})\n        '
        processed.append(key)
    map_data += '\n'

# Save tilemap data
tilemap_data_file_name = f'{prefix}.txt'
with open(os.path.join(assets_dir, tilemap_data_file_name), 'w') as tilemap_data_file:
    tilemap_data_file.write(map_data)

# Generate and save layout code
layout_name = f'activity_{prefix}'
with open('layout.xml', 'r') as layout_template:
    template = layout_template.read()

    with open(os.path.join(layout_dir, f'{layout_name}.xml'), 'w') as generated_layout:
        generated_layout.write(template)

# Generate and save activity code
activity_name = f'{prefix.capitalize()}Activity'
with open('Activity.kt', 'r') as activity_template:
    template = activity_template.read()
    template = template.replace('ACTIVITY_NAME', activity_name)
    template = template.replace('LAYOUT_NAME', layout_name)
    template = template.replace('MAP_DATA_FILE', tilemap_data_file_name)
    template = template.replace('MAP_LOOKUP_TABLE', map_lookup_table)

    with open(os.path.join(source_dir, f'{activity_name}.kt'), 'w') as generated_activity:
        generated_activity.write(template)
