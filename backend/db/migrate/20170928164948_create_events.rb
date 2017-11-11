class CreateEvents < ActiveRecord::Migration[5.1]
  def change
    create_table :events do |t|
      t.belongs_to :room
      t.belongs_to :company

      t.date :date
      t.time :start_time
      t.time :end_time
      t.string :type
      t.string :description

      t.timestamps
    end
  end
end
